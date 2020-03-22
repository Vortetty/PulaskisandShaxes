package net.vortetty.pulaskisandshaxes.gens;

import net.devtech.rrp.entrypoint.RRPPreGenEntrypoint;

public class Entrypoint implements RRPPreGenEntrypoint {
	@Override
	public void register() {
		new PulaskiGen("pulaskisandshaxes","netherite_pulaski_test","ffffff","00ffff","ffff00","ff00ff","ff0000","00ff00","0000ff","777777");
		new ShaxeGen("pulaskisandshaxes","netherite_shaxe_test","ffffff","00ffff","ffff00","ff00ff","ff0000","00ff00","0000ff","777777");
	}
}
