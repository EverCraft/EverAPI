/**
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import fr.evercraft.everapi.EverAPI;

public class UtilsDate {
	private static final int DEFAULT_LENGTH = 3;
	private final EverAPI plugin;
	private final int[] types;
	private String[] names;

	public UtilsDate(final EverAPI plugin){
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
		this.names = new String[] { 
				this.plugin.getMessages().getMessage("TIME_YEAR"),
				this.plugin.getMessages().getMessage("TIME_YEARS"), 
				this.plugin.getMessages().getMessage("TIME_MONTH"),
				this.plugin.getMessages().getMessage("TIME_MONTHS"),
				this.plugin.getMessages().getMessage("TIME_DAY"),
				this.plugin.getMessages().getMessage("TIME_DAYS"),
				this.plugin.getMessages().getMessage("TIME_HOUR"),
				this.plugin.getMessages().getMessage("TIME_HOURS"),
				this.plugin.getMessages().getMessage("TIME_MINUTE"),
				this.plugin.getMessages().getMessage("TIME_MINUTES"),
				this.plugin.getMessages().getMessage("TIME_SECOND"),
				this.plugin.getMessages().getMessage("TIME_SECONDS")};
	}
	
	public String formatDateDiff(final long date) {
		return formatDateDiff(date, DEFAULT_LENGTH);
	}

	public String formatDateDiff(final long date, final int length) {
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(date);
		Calendar now = new GregorianCalendar();
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
		
		if(toDate.equals(fromDate)) {
			resultat = this.plugin.getMessages().getMessage("TIME_NOW");
		} else {
			List<String> sb = new ArrayList<String>();
			int cpt = 0;
			while(cpt < this.types.length && sb.size() <= length) {
				Integer diff = dateDiff(this.types[cpt], fromDate, toDate, future);
				if (diff >= 2) {
					sb.add(this.names[cpt * 2 + 1].replaceAll("<value>", diff.toString()));
				} else if(diff >= 1) {
					sb.add(this.names[cpt * 2].replaceAll("<value>", diff.toString()));
				}
				cpt++;
			}
			if (sb.size() == 0) {
				resultat = this.plugin.getMessages().getMessage("TIME_NOW");
			} else {
				resultat = String.join(this.plugin.getMessages().getMessage("TIME_JOIN"), sb);
			}
		}
		return resultat;
	}
	
	private int dateDiff(final int type, final Calendar fromDate, final Calendar toDate, final boolean future) {
		int diff = 0;
		long savedDate = fromDate.getTimeInMillis();
		while ((future && !fromDate.after(toDate)) || (!future && !fromDate.before(toDate))) {
			savedDate = fromDate.getTimeInMillis();
			if(future) {
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
}
