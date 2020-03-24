## Modding

- to add the Pulaskis and Shaxes support badge use ``![Supports Pulaskis and Shaxes](https://raw.githubusercontent.com/Vortetty/random-files/master/Badge.png)`` 
- here is what the badge looks like: ![Supports Pulaskis and Shaxes](https://raw.githubusercontent.com/Vortetty/random-files/master/Badge.png)

- to add a pulaski/shaxe to your mod, ``import net.vortetty.pulaskisandshaxes.items.PulaskiItem;`` or ``import net.vortetty.pulaskisandshaxes.items.ShaxeItem;`` and use it as a normal item type.


to include this in your project:

### build.gradle

```
repositories {
	maven {
		url = 'https://raw.githubusercontent.com/Vortetty/maven/master/'
	}
}

dependencies {
  modCompile group: 'net.vortetty', name: 'pulaskisandshaxes', version: '1.1.2'
}
```

### New Feature, Runtime Texture Gen

To use this, import ``net.vortetty.pulaskisandshaxes.gens.PulaskiGen`` or  ``net.vortetty.pulaskisandshaxes.gens.ShaxeGen``.

an example is: 

```java

new PulaskiGen("pulaskisandshaxes","netherite_pulaski_test","ffffff","00ffff","ffff00","ff00ff","ff0000","00ff00","0000ff","777777");
``` 
that generates ![Sample Pulaski](https://raw.githubusercontent.com/Vortetty/random-files/master/download.png) (colors are odd so that you can see what things do what.)
and the actual method definition is:

```java
PulaskiGen(String modid, String path, String lightedge, String darkedge, String lightbody, String mediumbody, String darkbody, String sticklight, String stickdark, String stickmiddle)
```

im working on simple versions where you can provide a color for the blade and stick, or just the blade to generate textures.

### Using Pre-Runtime Texture Gen

to start you need a new class, call it whatever you want i'm using this:

```java
import net.devtech.rrp.entrypoint.RRPPreGenEntrypoint;  
  
public class Entrypoint implements RRPPreGenEntrypoint {  
  @Override  
  public void register() {
	  // Put anything you want to run alongside the game load here. 
	  // it starts a new thread so everything finishes sooner
	  // using this is usually faster than mojang texture loading
  }  
}
```

You also need to add this to fabric.mod.json and replace ``&lt;Fully-Qualified Class Name Here&gt;`` with the Fully-Qualified Class Name of the class implementing ``RRPPreGenEntrypoint``:

``` json
"entrypoints": {  
 "rrp_pre": [  
  "&lt;Fully-Qualified Class Name Here&gt;"  
 ]
},
```

i have ``net.devtech.rrp`` packaged in my mod, no need to include it in yours :) 
