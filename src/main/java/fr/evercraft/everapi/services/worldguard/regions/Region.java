package fr.evercraft.everapi.services.worldguard.regions;

import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3i;

import javax.annotation.Nullable;

import org.spongepowered.api.world.World;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Represents a physical shape.
 */
public interface Region extends Cloneable {

	/**
	 * Get the lower point of a region.
	 *
	 * @return min. point
	 */
	public Vector3i getMinimumPoint();

	/**
	 * Get the upper point of a region.
	 *
	 * @return max. point
	 */
	public Vector3i getMaximumPoint();

	/**
	 * Get the center point of a region.
	 * Note: Coordinates will not be integers
	 * if the corresponding lengths are even.
	 *
	 * @return center point
	 */
	public Vector3i getCenter();

	/**
	 * Get the number of blocks in the region.
	 *
	 * @return number of blocks
	 */
	public int getArea();

	/**
	 * Get X-size.
	 *
	 * @return width
	 */
	public int getWidth();

	/**
	 * Get Y-size.
	 *
	 * @return height
	 */
	public int getHeight();

	/**
	 * Get Z-size.
	 *
	 * @return length
	 */
	public int getLength();

	/**
	 * Expand the region.
	 *
	 * @param changes array/arguments with multiple related changes
	 * @throws RegionOperationException
	 */
	public void expand(Vector3i... changes) throws RegionOperationException;

	/**
	 * Contract the region.
	 *
	 * @param changes array/arguments with multiple related changes
	 * @throws RegionOperationException
	 */
	public void contract(Vector3i... changes) throws RegionOperationException;

	/**
	 * Shift the region.
	 *
	 * @param change the change
	 * @throws RegionOperationException
	 */
	public void shift(Vector3i change) throws RegionOperationException;

	/**
	 * Returns true based on whether the region contains the point.
	 *
	 * @param position the position
	 * @return true if contained
	 */
	public boolean contains(Vector3i position);

	/**
	 * Get a list of chunks.
	 *
	 * @return a list of chunk coordinates
	 */
	public Set<Vector2i> getChunks();

	/**
	 * Return a list of 16*16*16 chunks in a region
	 *
	 * @return the chunk cubes this region overlaps with
	 */
	public Set<Vector3i> getChunkCubes();

	/**
	 * Sets the world that the selection is in.
	 *
	 * @return the world, or empty
	 */
	public Optional<World> getWorld();

	/**
	 * Sets the world that the selection is in.
	 *
	 * @param world the world, which may be null
	 */
	public void setWorld(@Nullable World world);

	/**
	 * Make a clone of the region.
	 *
	 * @return a cloned version
	 */
	public Region clone();

	/**
	 * Polygonizes a cross-section or a 2D projection of the region orthogonal to the Y axis.
	 *
	 * @param maxPoints maximum number of points to generate. -1 for no limit.
	 * @return the points.
	 */
	public List<Vector2i> polygonize(int maxPoints);
}
