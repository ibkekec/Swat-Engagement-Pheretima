package de.paluno.game.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import de.paluno.game.WeaponType;
import de.paluno.game.WindDirectionIndicator;
import de.paluno.game.gameobjects.HealthBar;
import de.paluno.game.gameobjects.Player;
import de.paluno.game.gameobjects.Projectile;
import de.paluno.game.gameobjects.ShotDirectionIndicator;
import de.paluno.game.gameobjects.Virus;
import de.paluno.game.gameobjects.Weapon;
import de.paluno.game.gameobjects.Worm;
import de.paluno.game.gameobjects.ground.Ground;

public class Snapshot implements Cloneable {
	World world;
    Ground ground;
    HealthBar healthbar;
    Player player;
    Projectile projectile;
    ShotDirectionIndicator shotdirectionindicator;
    Worm worm;
    Virus virus;
    Weapon weapon;
    WindDirectionIndicator winddirectionindicator;
    PlayScreen playscreen;
    WeaponType weapontype;
    int zahl;
    private Vector2 position;
    private Vector2 direction;
    private AssetManager assets;
    int playernum;
    
	public Snapshot(World world) {
		this.world=world;
	}
	
	public Ground makesnapshotground() {
		
		 //Ground clone = new Ground(playscreen);
		//	clone.setCloningParameters(ground);
		//return clone;
		return null;
	}
	public HealthBar makesnapshotHealhtbar() {

		 //HealthBar clone = new HealthBar(playscreen, worm);
		//	clone.setCloningParameters(healthbar);
		//return clone;
        return null;
	}
		
	
	public Player cloneplayer() {
		
		 //Player clone = new Player(playernum,world,assets);
		//	clone.setCloningParameters(player);
		//return clone;
        return null;
	}
	
	public Projectile cloneprojectile() {
		 //Projectile clone = new Projectile(playscreen,position,direction);
		//	clone.setCloningParameters(projectile);
		//return clone;
		 
	return null;
		
	}
	public ShotDirectionIndicator cloneshotdirectionindicator() {
		
		 //ShotDirectionIndicator clone = new ShotDirectionIndicator(zahl,worm,playscreen);
		//	clone.setCloningParameters(shotdirectionindicator);
		//return clone;
		 return null;
	}
	 public Virus clonevirus() {
		 //Virus clone = new Virus(worm,playscreen);
		//	clone.setCloningParameters(virus);
		//return clone;
		 return null;
	 }
	
	
	public WindDirectionIndicator  clonewinddirectionindicator() {
		 
			 //WindDirectionIndicator clone = new WindDirectionIndicator();
			//	clone.setCloningParameters(winddirectionindicator);
			//return clone;
			 return null;
		 }
		
	
	 public Weapon cloneweapon() {
		 //Weapon clone = new Weapon(worm,weapontype);
		//	clone.setCloningParameters(weapon);
		//return clone;
		 return null;
	 }
	
}	
