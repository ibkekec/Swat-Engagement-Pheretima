package de.paluno.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.paluno.game.Assets;
import de.paluno.game.gameobjects.WeaponType;
import de.paluno.game.gameobjects.Player;
import de.paluno.game.worldhandlers.WorldHandler;

import java.util.ArrayList;

public class WeaponUI {

    private PlayScreen playScreen;
    private ElementGUI elementGUI;
    //takes the whole screen
    private Stage stage;
    // Icons
    private ImageButton buttonGun, buttonGrenade, buttonBazooka, buttonWeaponSpecial,
            buttonAirStrike, buttonTeleport, buttonMine, buttonArtillery, weaponMenuButton;
    // Is implemented into the stage
    private Table table, table2, tableMain;

    // Background of the table
    private Image image, image2;
    private WorldHandler worldHandler;


    public WeaponUI(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.elementGUI = new ElementGUI();

        // Table Background
        image = new Image((new TextureRegionDrawable(new TextureRegion(playScreen.getAssetManager().get(Assets.weaponUI)))));
        image2 = new Image(new TextureRegionDrawable(new TextureRegion(playScreen.getAssetManager().get(Assets.weaponUI2))));

        //Gun ElementGUI
        buttonGun = elementGUI.createWeaponButton(playScreen.getAssetManager().get(Assets.iconGun));
        buttonGun.addListener((new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Gun ElementGUI Clicked");
                worldHandler.applyEquipWeapon(WeaponType.WEAPON_GUN);
                table.setVisible(false);
                table2.setVisible(false);

            }
        }));


        // Grenade ElementGUI
        buttonGrenade = elementGUI.createWeaponButton((playScreen.getAssetManager().get(Assets.iconGrenade)));
        buttonGrenade.addListener((new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Grenade ElementGUI Clicked");
                worldHandler.applyEquipWeapon(WeaponType.WEAPON_GRENADE);
                table.setVisible(false);
                table2.setVisible(false);

            }
        }));

        // Bazooka ElementGUI
        buttonBazooka = elementGUI.createWeaponButton(playScreen.getAssetManager().get(Assets.iconBazooka));
        buttonBazooka.addListener((new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Bazooka ElementGUI Clicked");
                worldHandler.applyEquipWeapon(WeaponType.WEAPON_BAZOOKA);
                table.setVisible(false);
                table2.setVisible(false);
            }
        }));

        // SpecialWeapon ElementGUI
        buttonWeaponSpecial = elementGUI.createWeaponButton(playScreen.getAssetManager().get(Assets.iconSpecial));
        buttonWeaponSpecial.addListener((new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("SpecialWeapon ElementGUI Clicked");
                worldHandler.applyEquipWeapon(WeaponType.WEAPON_SPECIAL);
                table.setVisible(false);
                table2.setVisible(false);
            }
        }));

        stage = new Stage();
        table = new Table();
        tableMain = new Table();
        tableMain.setFillParent(true);


        table.setBackground(image.getDrawable());

        // Positioning of Buttons
        table.row();
        table.add(buttonGun);
        table.row();
        table.add(buttonGrenade);
        table.row();
        table.add(buttonBazooka);
        table.row();
        table.add(buttonWeaponSpecial);
        stage.setDebugAll(false);

        //sets space to the edge of table
        table.padRight(7);
        table.padTop(2);


        // Airstrike ElementGUI
        buttonAirStrike = elementGUI.createWeaponButton(playScreen.getAssetManager().get(Assets.iconAirStrike));
        buttonAirStrike.addListener((new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Airstrike ElementGUI Clicked");
                table.setVisible(false);
                table2.setVisible(false);
            }
        }));

        // Teleport ElementGUI
        buttonTeleport = elementGUI.createWeaponButton(playScreen.getAssetManager().get(Assets.iconTeleport));
        buttonTeleport.addListener((new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Teleport Clicked");
                table.setVisible(false);
                table2.setVisible(false);
                worldHandler.applyEquipWeapon(WeaponType.WEAPON_TELEPORTER);

            }
        }));


        // Mine ElementGUI
        buttonMine = elementGUI.createWeaponButton(playScreen.getAssetManager().get(Assets.iconMine));
        buttonMine.addListener((new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Mine ElementGUI Clicked");
                table.setVisible(false);
                table2.setVisible(false);
            }
        }));

        // SpecialWeapon ElementGUI
        buttonArtillery = elementGUI.createWeaponButton(playScreen.getAssetManager().get(Assets.iconArtillery));
        buttonArtillery.addListener((new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Artillery ElementGUI Clicked");
                table.setVisible(false);
                table2.setVisible(false);
            }
        }));

        //Weapon Menu Button
        weaponMenuButton = elementGUI.createWeaponButton(playScreen.getAssetManager().get(Assets.weaponMenuButton));
        weaponMenuButton.addListener(new ClickListener() {
            int weaponClick = 0;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (weaponClick == 0) {
                    table.setVisible(true);
                    table2.setVisible(true);
                    weaponClick = 1;
                } else {
                    table.setVisible(false);
                    table2.setVisible(false);
                    weaponClick = 0;
                }


                System.out.println("Weapon Menu Clicked");
            }
        });

        table2 = new Table();

        table2.setBackground(image2.getDrawable());

        table2.add(buttonAirStrike);
        table2.add(buttonTeleport);
        table2.add(buttonArtillery);
        table2.add(buttonMine);
        table2.padTop(35);
        stage.setDebugAll(false);
        buttonMine.pad(5);

        buttonArtillery.pad(5);
        buttonAirStrike.pad(5);
        buttonTeleport.pad(5);


        tableMain.right();
        tableMain.add(weaponMenuButton).right().row();
        tableMain.add(table.right()).size(92, 150).right().padTop(10).row();
        tableMain.add(table2).padTop(10).size(166,75);


        stage.addActor(tableMain);

        table.setVisible(false);
        table2.setVisible(false);
    }


    public void render(SpriteBatch batch, float delta) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    // Listener for Buttons
    public Stage getInputProcessor() {
        return stage;
    }

    public void setWorldHandler(WorldHandler worldHandler) {
        this.worldHandler = worldHandler;
    }
}


