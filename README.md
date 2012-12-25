yUML tool for Astah
============================
This is an [Astah](http://astah.net/) plug-in which converts Astah diagrams into simple [yUML](http://yuml.me/) diagrams.

It is drawing yUML diagrams by using the service at yUML. 

<img src="astah-yuml-tool-plugin/raw/master/src/main/images/screenshot.png" width="720"/>

What is yUML?
---
>yUML is an online tool for creating and publishing simple UML diagrams. It's makes it really easy for you to:

> * Embed UML diagrams in blogs, emails and wikis.
> * Post UML diagrams in forums and blog comments.
> * Use directly within your web based bug tracking tool.
> * Copy and paste UML diagrams into MS Word documents and Powerpoint presentations.
>
>[
What is yUML?  -  yUML FAQ](http://yuml.me/faq)

<img src="http://yuml.me/diagram/scruffy;scale:80/class/[Object]^-[System],[Object]^-[ClassLoader],[ClassLoader]^-[SecureClassLoader]"/>

Available for
---
[Astah Community](http://astah.net/editions/community), [Astah UML](http://astah.net/editions/uml), [Astah Professional](http://astah.net/editions/professional) (6.5 or later)


How to install
---
[Download jar file](https://github.com/ChangeVision/astah-yuml-tool-plugin/downloads).
Deploy the jar file you downloaded from [yUML tool for Astah](https://github.com/ChangeVision/astah-yuml-tool-plugin/downloads), in the **"plugins"** folder…

 * e.g.) for Community edition
   
   `$USER_HOME/.astah/community/plugins/`
   
   `/Applications/astah community/plguins/`
   
   `C:¥Program Files¥astah-community¥plugins¥`

 * e.g.) for UML edition
   
   `$USER_HOME/.astah/uml/plugins/`
   
   `/Applications/astah UML/plguins/`
   
   `C:¥Program Files¥astah-UML¥plugins¥`
   
 * e.g.) for Professional edition
   
   `$USER_HOME/.astah/professional/plugins/`
   
   `/Applications/astah professional/plguins/`
   
   `C:¥Program Files¥astah-professional¥plugins/`

Usage
---
1. Launch Astah
1. Open the .asta file
1. [yUML] is added under the [Tool]
1. Select [Tool] - [yUML] - [Convert to yUML diagrams]
   <img src="astah-yuml-tool-plugin/raw/master/src/main/images/menu.png" width="640"/>
1. Select a Class/Usecase diagram to convert into yUML diagram from the diagram list
   <img src="astah-yuml-tool-plugin/raw/master/src/main/images/screenshot.png" width="640"/>

How to build
---
1. Install the Astah Plug-in SDK - <http://astah.net/features/sdk>
1. `git clone git://github.com/ChangeVision/astah-yuml-tool-plugin.git`
1. `cd astah-yuml-tool-plugin`
1. `astah-build`
1. `astah-launch`

### Generating config files [for Eclipse](http://astah.net/tutorials/plug-ins/plugin_tutorial_en/html/helloworld.html#eclipse)

 * `astah-mvn eclipse:eclipse`

License
---
Copyright 2012 Change Vision, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this work except in compliance with the License.
You may obtain a copy of the License in the LICENSE file, or at:

   <http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
