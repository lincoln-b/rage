# rage

Visual representations of ClojureScript Abstract Syntax Trees, in the browser.

See it in action!
* Live demo: http://vps124502.vps.ovh.ca/rage/resources/public/
* URL shortened version: http://tiny.cc/cljs-ast

## Overview

The goal of this project is to have a graphical representation of the AST of any ClojureScript code.

As it stands, you can view the pretty-printed text version of the AST alongside your code.

Milestones:
1. <s>Change the textarea to an actual code editor.</s>
2. Come up with a graphical way to represent ClojureScript ASTs.
3. Add a canvas pane with drag/drop, zoom in/out.
4. Move the textual AST to the bottom left, change the right pane to be the graphical AST.

## Setup

`git clone ....`

`cd rage`

`lein figwheel`

## License

Copyright © 2017

Distributed under the Eclipse Public License version 1.0.
