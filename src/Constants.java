/*
 * Copyright 2013 Inmite s.r.o. (www.inmite.eu).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Constants for various stuff used in whole plugin.
 * @author David Vávra (david@inmite.eu)
 * refactoring to regexp Jan Mach (jan.mach71@gmail.com)
 */
public class Constants {
    public static final Pattern NORMAL = Pattern.compile("(.*?)(((_n)(\\.|_))|(_normal))(.*?)");
    public static final Pattern FOCUSED = Pattern.compile("(.*?)(((_f)(\\.|_))|(_focused))(.*?)");
    public static final Pattern PRESSED = Pattern.compile("(.*?)(((_p)(\\.|_))|(_pressed))(.*?)");
    public static final Pattern SELECTED = Pattern.compile("(.*?)(((_s)(\\.|_))|(_selected))(.*?)");
    public static final Pattern CHECKED = Pattern.compile("(.*?)(((_c)(\\.|_))|(_checked))(.*?)");
    public static final Pattern DISABLED = Pattern.compile("(.*?)(((_d)(\\.|_))|(_disabled))(.*?)");
    public static final Pattern HOVERED = Pattern.compile("(.*?)(((_h)(\\.|_))|(_hovered))(.*?)");
    public static final Pattern CHECKABLE = Pattern.compile("(.*?)(((_k)(\\.|_))|(_checkable))(.*?)");
    public static final Pattern ACTIVATED = Pattern.compile("(.*?)(((_a)(\\.|_))|(_activated))(.*?)");
    public static final Pattern WINDOW_FOCUSED = Pattern.compile("(.*?)(((_w)(\\.|_))|(_window_focused))(.*?)");

    public static Pattern[] SUFFIXES = new Pattern[]{NORMAL, PRESSED, FOCUSED, SELECTED, CHECKED, DISABLED, HOVERED, CHECKABLE, ACTIVATED, WINDOW_FOCUSED};
	public static Pattern VALID_FOLDER_PATTERN = Pattern.compile("^drawable(-[a-zA-Z0-9]+)*$");
	public static String EXPORT_FOLDER = "drawable";
    public static String REPLACE_WITH = "$1$5$7";
	public static HashMap<Pattern, State> sMapping;

	static {
		// mapping from file suffixes into android attributes and their default values
		sMapping = new HashMap<Pattern, State>();
		sMapping.put(FOCUSED, new State("state_focused", false));
		sMapping.put(PRESSED, new State("state_pressed", false));
		sMapping.put(SELECTED, new State("state_selected", false));
		sMapping.put(CHECKED, new State("state_checked", false));
		sMapping.put(DISABLED, new State("state_enabled", true));
		sMapping.put(HOVERED, new State("state_hovered", false));
		sMapping.put(CHECKABLE, new State("state_checkable", false));
		sMapping.put(ACTIVATED, new State("state_activated", false));
		sMapping.put(WINDOW_FOCUSED, new State("state_window_focused", false));
	}

	static class State {
		public String attributeName;
		public boolean defaultValue;

		State(String attributeName, boolean defaultValue) {
			this.attributeName = attributeName;
			this.defaultValue = defaultValue;
		}
	}

}
