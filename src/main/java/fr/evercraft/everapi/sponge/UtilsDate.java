/*
 * This file is part of EverAPI.
 *
 * EverAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.evercraft.everapi.sponge;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.EverAPI;

public class UtilsDate {
	
	private static Pattern TIME_PATTERN = Pattern.compile("(?:([0-9]+)\\s*(years|year|y)+[,\\s]*)?" + 
															"(?:([0-9]+)\\s*(months|month|mo)+[,\\s]*)?" + 
															"(?:([0-9]+)\\s*(weeks|week|w)+[,\\s]*)?" + 
															"(?:([0-9]+)\\s*(days|day|d)+[,\\s]*)?" + 
															"(?:([0-9]+)\\s*(hours|hour|h)+[,\\s]*)?" + 
															"(?:([0-9]+)\\s*(minutes|minute|m)+[,\\s]*)?" +
															"(?:([0-9]+)\\s*(seconds|second|s)+[,\\s]*)?", Pattern.CASE_INSENSITIVE);

	private static final int DEFAULT_LENGTH = 3;
	private final EverAPI plugin;
	private final int[] types;
	private String[] names;
	
	private String formatDate;
	private String formatTime;
	private String formatDateTime;

	public UtilsDate(final EverAPI plugin) {
		this.plugin = plugin;
		
		this.types = new int[] { 
				Calendar.YEAR, 
				Calendar.MONTH,
				Calendar.DAY_OF_MONTH, 
				Calendar.HOUR_OF_DAY, 
				Calendar.MINUTE,
				Calendar.SECOND};
		
		reload();
	}
	
	public void reload() {
		this.formatDate = this.plugin.getConfigs().getFormatDate();
		this.formatTime = this.plugin.getConfigs().getFormatTime();
		this.formatDateTime = this.plugin.getConfigs().getFormatDateTime();
		
		this.names = new String[] { 
				EAMessages.TIME_YEAR.getString(),
				EAMessages.TIME_YEARS.getString(), 
				EAMessages.TIME_MONTH.getString(),
				EAMessages.TIME_MONTHS.getString(),
				EAMessages.TIME_DAY.getString(),
				EAMessages.TIME_DAYS.getString(),
				EAMessages.TIME_HOUR.getString(),
				EAMessages.TIME_HOURS.getString(),
				EAMessages.TIME_MINUTE.getString(),
				EAMessages.TIME_MINUTES.getString(),
				EAMessages.TIME_SECOND.getString(),
				EAMessages.TIME_SECONDS.getString()};
	}
	
	/*
	 * Utils
	 */
	
	public String parseDate() {
		return parseDate(System.currentTimeMillis());
	}
	
	public String parseDate(long time) {
		return (new SimpleDateFormat(this.formatDate)).format(new Timestamp(time));
	}
	
	public String parseTime() {
		return parseTime(System.currentTimeMillis());
	}
	
	public String parseTime(long time) {
		return (new SimpleDateFormat(this.formatTime)).format(new Timestamp(time));
	}
	
	public String parseDateTime() {
		return parseDateTime(System.currentTimeMillis());
	}
	
	public String parseDateTime(long time) {
		return (new SimpleDateFormat(this.formatDateTime)).format(new Timestamp(time));
	}
	
	/*
	 * Différence
	 */
	
	public String diff(final long date) {
		return formatDate(date + System.currentTimeMillis(), DEFAULT_LENGTH);
	}
	
	public String formatDate(final long date) {
		return formatDate(date, DEFAULT_LENGTH);
	}

	public String formatDate(final long date, final int length) {
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(date);
		Calendar now = new GregorianCalendar();
		return this.formatDateDiff(now, c, length);
	}
	
	public String formatDateDiff(final long fromDate, final long toDate) {
		return this.formatDateDiff(fromDate, toDate, DEFAULT_LENGTH);
	}
	
	public String formatDateDiff(final long fromDate, final long toDate, final int length) {
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(toDate);
		Calendar now = new GregorianCalendar();
		now.setTimeInMillis(fromDate);
		return this.formatDateDiff(now, c, length);
	}

	public String formatDateDiff(final Calendar fromDate, final Calendar toDate) {
		return formatDateDiff(fromDate, toDate, DEFAULT_LENGTH);
	}
	
	public String formatDateDiff(final Calendar fromDate, final Calendar toDate, final int length) {
		String resultat;
		boolean future = false;
		if (toDate.after(fromDate)) {
			future = true;
		}
		
		if (toDate.equals(fromDate)) {
			resultat = EAMessages.TIME_NOW.getString();
		} else {
			List<String> sb = new ArrayList<String>();
			int cpt = 0;
			while(cpt < this.types.length && sb.size() <= length) {
				Integer diff = dateDiff(this.types[cpt], fromDate, toDate, future);
				if (diff >= 2) {
					sb.add(this.names[cpt * 2 + 1].replace("{value}", diff.toString()));
				} else if (diff >= 1) {
					sb.add(this.names[cpt * 2].replace("{value}", diff.toString()));
				}
				cpt++;
			}
			if (sb.size() == 0) {
				resultat = EAMessages.TIME_NOW.getString();
			} else {
				resultat = String.join(EAMessages.TIME_JOIN.getString(), sb);
			}
		}
		return resultat;
	}
	
	private int dateDiff(final int type, final Calendar fromDate, final Calendar toDate, final boolean future) {
		int diff = 0;
		long savedDate = fromDate.getTimeInMillis();
		while ((future && !fromDate.after(toDate)) || (!future && !fromDate.before(toDate))) {
			savedDate = fromDate.getTimeInMillis();
			if (future) {
				fromDate.add(type, 1);
			} else {
				fromDate.add(type, -1);
			}
			diff++;
		}
		diff--;
		fromDate.setTimeInMillis(savedDate);
		return diff;
	}
	
	public Optional<String> parseDuration(String duration_string) {
		long time = System.currentTimeMillis();
		Optional<Long> date = UtilsDate.parseDuration(time, duration_string, true);
		if (date.isPresent()) {
			return Optional.of(this.formatDateDiff(time, date.get()));
		}
		return Optional.empty();
	}
	
	public static Optional<Long> parseDuration(String time_string, boolean future) {
		return UtilsDate.parseDuration(System.currentTimeMillis(), time_string, future);
	}
	
	public static Optional<Long> parseDuration(long creation, String time_string, boolean future) {
		Matcher m = TIME_PATTERN.matcher(time_string);
		int years = 0;
		int months = 0;
		int weeks = 0;
		int days = 0;
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		boolean found = false;
		
		if (m.find() && m.group() != null && !m.group().isEmpty() && m.group().equalsIgnoreCase(time_string)) {
			found = true;
			if (m.group(1) != null && !m.group(1).isEmpty()){
				years = Integer.parseInt(m.group(1));
			}
			if (m.group(3) != null && !m.group(3).isEmpty()){
				months = Integer.parseInt(m.group(3));
			}
			if (m.group(5) != null && !m.group(5).isEmpty()){
				weeks = Integer.parseInt(m.group(5));
			}
			if (m.group(7) != null && !m.group(7).isEmpty()){
				days = Integer.parseInt(m.group(7));
			}
			if (m.group(9) != null && !m.group(9).isEmpty()){
				hours = Integer.parseInt(m.group(9));
			}
			if (m.group(11) != null && !m.group(11).isEmpty()){
				minutes = Integer.parseInt(m.group(11));
			}
			if (m.group(13) != null && !m.group(13).isEmpty()){
				seconds = Integer.parseInt(m.group(13));
			}
		}
		
		if (!found){
			return Optional.empty();
		}
		
		Calendar calendar = new GregorianCalendar();
		int value = future ? 1 : -1;
		
		if (years > 0) {
			calendar.add(Calendar.YEAR, years * value);
		}
		if (months > 0) {
			calendar.add(Calendar.MONTH, months * value);
		}
		if (weeks > 0) {
			calendar.add(Calendar.WEEK_OF_YEAR, weeks * value);
		}
		if (days > 0) {
			calendar.add(Calendar.DAY_OF_MONTH, days * value);
		}
		if (hours > 0) {
			calendar.add(Calendar.HOUR_OF_DAY, hours * value);
		}
		if (minutes > 0) {
			calendar.add(Calendar.MINUTE, minutes * value);
		}
		if (seconds > 0) {
			calendar.add(Calendar.SECOND, seconds * value);
		}
		
		Calendar max = new GregorianCalendar();
		max.add(Calendar.YEAR, 10);
		if (calendar.after(max)) {
			return Optional.of(max.getTimeInMillis());
		}
		return Optional.of(calendar.getTimeInMillis());
	}
}
