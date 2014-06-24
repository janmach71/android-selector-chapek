# SelectorChapek for Android

This Android Studio plugin automatically generates drawable selectors from appropriately named Android resources.

## How to install it:

 - in Android Studio: go to `Preferences→Plugins→Browse repositories` and search for `SelectorChapek`

 _or_

 - [download it](http://plugins.jetbrains.com/plugin/7298) and install via `Preferences→Plugins→Install plugin from disk`

## How to use it:

1) Right-click folder with your resources e.g 'drawable-xhdpi'

 ![](img/select_folder.png)

2) Select 'Generate Android Selectors'

 ![](img/select_option.png)

3) All selectors _automagically_ appear in the 'drawable' folder!

 ![](img/selectors_generated.png)

## Naming convention:
In order of plugin to work, resources need to be appropriately named. The plugin supports png or nine-patch files with certain suffices anywhere in the file name. You can also combine any infixes together.
In case of using short single letter naming convention, the letter must be preceded by '_' and followed by '_' or '.'. You can also combine single letter identifiers together.

### Mapping 

| File name infix 	|File name short | Drawable state 		    |
| ------------------| ---------------| ---------------------    |
| _normal 		   	| _n_ | _n.      | (default state)          |
| _pressed         	| _s_ | _s.      | state_pressed			|
| _focused         	| _f_ | _f.      | state_focused			|
| _disabled        	| _d_ | _d.      | state_enabled (false)    |
| _checked		   	| _c_ | _c.      | state_checked   		    |
| _selected		   	| _s_ | _s.      | state_selected  		    |
| _hovered         	| _h_ | _h.      | state_hovered   	   	    |
| _checkable	   	| _k_ | _k.      | state_checkable 		    |
| _activated	   	| _a_ | _a.      | state_activated 		    |
| _windowfocused   	| _w_ | _w.      | state_window_focused 	|


## Wishlist

 - UI which will tell you which selectors will be generated
 - possibility to change naming mapping

_Pull requests are welcomed!_

## Why 'Chapek'?

<img src="http://upload.wikimedia.org/wikipedia/commons/b/bd/Karel-capek.jpg" width="60"  align="right"/>

[Karel Čapek](http://en.wikipedia.org/wiki/Karel_%C4%8Capek) was a famous Czech writer, inventor of the word 'robot'. The word comes from Czech word 'robota', which means 'hard manual labor'. As with Čapek's robots, this plugin will remove your unnecessary manual labor. 

See [**our other Czech personalities**](http://inmite.github.io) who help with [#AndroidDev](https://plus.google.com/s/%23AndroidDev).
