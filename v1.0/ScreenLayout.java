/*
 * This is just a dummy class/object of a LayoutManager interface
 * Technically, this class is useless, (as you may notice, all methods are empty).
 * However, the method "setLocation" of each component will only work if "setLayout" is given a proper LayoutManager object.
 * And that's why this dummy class/object is here.
 * It's only usage is to be instantiated, then use it's instance on the "setLayout" method of each component and container.
 */

import java.awt.*;

public class ScreenLayout implements LayoutManager
{
	@Override
	public void addLayoutComponent(String name, Component comp) {}

	@Override
	public void removeLayoutComponent(Component comp) {}

	@Override
	public Dimension preferredLayoutSize(Container parent)
	{
		return null;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent)
	{
		return null;
	}

	@Override
	public void layoutContainer(Container parent) {}
}

