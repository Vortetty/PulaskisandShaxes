import os, sys

modid = "pulaskisandshaxes"
id = "hoeing"

for i in sys.argv:
    if i != "test.py":
        print(i)
        pre, ext = os.path.splitext(i.split("\\")[-1])
        f= open(pre+".json","w+")
        f.write('{"parent":"minecraft:items/generated","textures":{"layer0":"'+modid+':items/'+pre+'"},"overrides":[{"predicate":{"'+modid+':'+id+'":1},"model":"'+modid+':items/'+pre+'_backwards"}]}')
        f.close()
        f= open(pre+"_backwards.json","w+")
        f.write('{"parent":"minecraft:items/generated","textures":{"layer0":"'+modid+':items/'+pre+'_backwards"}}')
        f.close()