package de.paluno.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.paluno.game.Assets;
import de.paluno.game.DataHandler;
import de.paluno.game.NetworkClient;
import de.paluno.game.SEPGame;
import de.paluno.game.interfaces.UserLoginRequest;
import de.paluno.game.interfaces.UserName;

import java.util.*;


public class LocalScreen extends ScreenAdapter implements Loadable {
    private SEPGame game;
    private Stage stage;
    private ElementGUI elementGUI;
    private TextField textFieldWorm1, textFieldWorm2, textFieldWorm3, textFieldWorm4, textFieldWorm5, textFieldUsername;
    private TextButton textButtonMenu, textButtonSpielen, textButtonAutoFill,textButtonAdd,textButtonDelete;;
    private Table tableBackground, tableTextField, tableTextButtonPlayer;
    private Image imageBackground;
    private NetworkClient client;
    public ImageButton buttonMap1, buttonMap2, buttonMap3, buttonMap4,
            buttonWorm1, buttonWorm2, buttonWorm3, buttonWorm4, buttonWorm5;
    //libgdx Array
    private Array<UserName> names = new Array<>();
    private int mapNumber, numWorms = 1;
    int playerNum = 1;
    private ScrollPane scrollPane;
    private List<UserName> list;

    private DataHandler dataHandler = new DataHandler() {
        @Override
        public void handleData(NetworkClient client, Object data) {
            if (data instanceof UserLoginRequest.Result) {
                game.setLobbyScreen(client);
            }
        }
    };

    private ArrayList<String> randomPlayerNames = new ArrayList<>(Arrays.asList("Julian", "Tarik", "Ibo", "Jan", "Steve",
            "Messi", "Ronaldo", "Kroos", "Neuer", "Ibrahimovic",
            "Batman", "Hulk", "Superman", "Spiderman", "Ant-Man",
            "Spongebob", "Patrick", "Sandy", "Plankton", "Mr. Krabs",
            "The Undertaker", "Rey Mysterio", "Jeff Hardy", "Hornswoggle", "The Rock",
            "Charlie Sheen", "Dexter", "Michael Scofield", "Barney Stinson", "Walter White",
            "Chris Brown", "Drake", "Michael Jackson", "Trey Songz", "Eminem"));

    public LocalScreen(SEPGame game) {
        super();
        this.game = game;
        stage = new Stage(new ScreenViewport());
        elementGUI = new ElementGUI();
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public boolean load(AssetManager manager) {
        Assets.loadAssets(manager, Assets.PlayerMenuScreenAssets);

        return false;
    }

    private void updateTextFields() {
        textFieldUsername.setText(list.getSelected().getUserName());
        textFieldWorm1.setText(list.getSelected().getWormNames()[0]);
        textFieldWorm2.setText(list.getSelected().getWormNames()[1]);
        textFieldWorm3.setText(list.getSelected().getWormNames()[2]);
        textFieldWorm4.setText(list.getSelected().getWormNames()[3]);
        textFieldWorm5.setText(list.getSelected().getWormNames()[4]);
    }

    @Override
    public void show() {
        names.add(new UserName("Spieler 1", new String[] { "Wurm 1", "Wurm 2", "Wurm 3", "Wurm 4", "Wurm 5"}));
        names.add(new UserName("Spieler 2", new String[] { "Wurm 1", "Wurm 2", "Wurm 3", "Wurm 4", "Wurm 5"}));

        tableBackground = new Table();
        tableTextField = new Table();
        if (client != null) {
            client.registerDataHandler(dataHandler);
        }
        tableBackground = new Table(elementGUI.getSkin());
        imageBackground = elementGUI.createBackground(game.getAssetManager().get(Assets.menuBackground));
        tableBackground.setBackground(imageBackground.getDrawable());
        tableBackground.setFillParent(true);

        textFieldWorm1 = elementGUI.createTextField("Worm 1");
        textFieldWorm1.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                list.getSelected().getWormNames()[0] = textFieldWorm1.getText();
                return true;
            }
        });
        textFieldWorm2 = elementGUI.createTextField("Worm 2");
        textFieldWorm2.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                list.getSelected().getWormNames()[1] = textFieldWorm2.getText();
                return true;
            }
        });
        textFieldWorm3 = elementGUI.createTextField("Worm 3");
        textFieldWorm3.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                list.getSelected().getWormNames()[2] = textFieldWorm3.getText();
                return true;
            }
        });
        textFieldWorm4 = elementGUI.createTextField("Worm 4");
        textFieldWorm4.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                list.getSelected().getWormNames()[3] = textFieldWorm4.getText();
                return true;
            }
        });
        textFieldWorm5 = elementGUI.createTextField("Worm 5");
        textFieldWorm5.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                list.getSelected().getWormNames()[4] = textFieldWorm5.getText();
                return true;
            }
        });
        textFieldUsername = elementGUI.createTextField("Username");
        textFieldUsername.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                list.getSelected().setUserName(textFieldUsername.getText());
                list.setItems(names);
                return true;
            }
        });
        textButtonAutoFill = elementGUI.createTextButton("Auto-Fill");
        textButtonAutoFill.addListener(new ClickListener() {
            int i = 0;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                    UserName name = list.getSelected();
                    name.getWormNames()[0] = randomPlayerNames.get(0 + i);
                    name.getWormNames()[1] = randomPlayerNames.get(1 + i);
                    name.getWormNames()[2] = randomPlayerNames.get(2 + i);
                    name.getWormNames()[3] = randomPlayerNames.get(3 + i);
                    name.getWormNames()[4] = randomPlayerNames.get(4 + i);
                    updateTextFields();
                    i += 5;
                    if (i >= randomPlayerNames.size())
                        i = 0;
            }
        });
        textButtonMenu = elementGUI.createTextButton("Menu");
        //textButtonMenu.setVisible(false);


        textButtonMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setModiScreen();
            }
        });

        textButtonSpielen = elementGUI.createTextButton("Spielen");

        textButtonMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });

        textButtonSpielen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (client != null) {
                    client.send(new UserLoginRequest(textFieldUsername.getText(), new String[]{textFieldWorm1.getText(),
                            textFieldWorm2.getText(), textFieldWorm3.getText(), textFieldWorm4.getText(), textFieldWorm5.getText()}));
                    //game.setLobbyScreen();
                } else {
                    game.setPlayScreen(mapNumber, numWorms, names);
                }
            }
        });

        buttonMap1 = elementGUI.createButton(game.getAssetManager().get(Assets.map1Thumbnail));
        buttonMap1.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                elementGUI.setSelectedImageButton(buttonMap1);
                mapNumber = 0;
                System.out.println("Map1 Clicked");
            }
        });


        buttonMap2 = elementGUI.createButton(game.getAssetManager().get(Assets.map2Thumbnail));
        buttonMap2.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                elementGUI.setSelectedImageButton(buttonMap2);
                mapNumber = 1;
                System.out.println("Map2 Clicked");
            }
        });


        buttonMap3 = elementGUI.createButton(game.getAssetManager().get(Assets.map3Thumbnail));
        buttonMap3.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                elementGUI.setSelectedImageButton(buttonMap3);

                mapNumber = 2;
                System.out.println("Map3 Clicked");
            }
        });


        buttonMap4 = elementGUI.createButton(game.getAssetManager().

                get(Assets.map4Thumbnail));
        buttonMap4.addListener(new ClickListener() {


            @Override
            public void clicked(InputEvent event, float x, float y) {
                elementGUI.setSelectedImageButton(buttonMap4);

                mapNumber = 3;
                System.out.println("Map4 Clicked");
            }
        });


        buttonWorm1 = elementGUI.createButton(game.getAssetManager().get(Assets.worms1Button));
        buttonWorm1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                textFieldWorm1.setVisible(true);
                textFieldWorm2.setVisible(false);
                textFieldWorm3.setVisible(false);
                textFieldWorm4.setVisible(false);
                textFieldWorm5.setVisible(false);

                elementGUI.setSelectedImageButton2(buttonWorm1);
                numWorms = 1;
                System.out.println("Worm 1 Clicked");
            }
        });


        buttonWorm2 = elementGUI.createButton(game.getAssetManager().get(Assets.worms2Button));
        buttonWorm2.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                textFieldWorm1.setVisible(true);
                textFieldWorm2.setVisible(true);
                textFieldWorm3.setVisible(false);
                textFieldWorm4.setVisible(false);
                textFieldWorm5.setVisible(false);


                elementGUI.setSelectedImageButton2(buttonWorm2);
                numWorms = 2;
                System.out.println("Worm 2 Clicked");
            }
        });

        buttonWorm3 = elementGUI.createButton(game.getAssetManager().get(Assets.worms3Button));
        buttonWorm3.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                textFieldWorm1.setVisible(true);
                textFieldWorm2.setVisible(true);
                textFieldWorm3.setVisible(true);
                textFieldWorm4.setVisible(false);
                textFieldWorm5.setVisible(false);

                elementGUI.setSelectedImageButton2(buttonWorm3);
                numWorms = 3;
                System.out.println("Worm 3 Clicked");
            }
        });

        buttonWorm4 = elementGUI.createButton(game.getAssetManager().get(Assets.worms4Button));
        buttonWorm4.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                textFieldWorm1.setVisible(true);
                textFieldWorm2.setVisible(true);
                textFieldWorm3.setVisible(true);
                textFieldWorm4.setVisible(true);
                textFieldWorm5.setVisible(false);

                elementGUI.setSelectedImageButton2(buttonWorm4);
                numWorms = 4;
                System.out.println("Worm 4 Clicked");
            }
        });

        buttonWorm5 = elementGUI.createButton(game.getAssetManager().get(Assets.worms5Button));
        buttonWorm5.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                textFieldWorm1.setVisible(true);
                textFieldWorm2.setVisible(true);
                textFieldWorm3.setVisible(true);
                textFieldWorm4.setVisible(true);
                textFieldWorm5.setVisible(true);
                elementGUI.setSelectedImageButton2(buttonWorm5);
                numWorms = 5;
                System.out.println("Worm 5 Clicked");
            }
        });

        textFieldWorm2.setVisible(false);
        textFieldWorm3.setVisible(false);
        textFieldWorm4.setVisible(false);
        textFieldWorm5.setVisible(false);

        tableBackground.setFillParent(true);
        tableBackground.add(new Table()).colspan(2).getActor().add(buttonMap1, buttonMap2, buttonMap3, buttonMap4);
        tableBackground.row();
        tableBackground.add(new Table()).colspan(2).getActor().add(buttonWorm1, buttonWorm2, buttonWorm3, buttonWorm4, buttonWorm5);
        tableBackground.row();



        tableTextField.add(textFieldUsername).size(150, 50).colspan(5).row();
        tableTextField.add(textFieldWorm1).size(150, 50);
        tableTextField.add(textFieldWorm2).size(150, 50);
        tableTextField.add(textFieldWorm3).size(150, 50);
        tableTextField.add(textFieldWorm4).size(150, 50);
        tableTextField.add(textFieldWorm5).size(150, 50).row();
        tableTextField.add(textButtonAutoFill).colspan(5);


        textButtonAdd = elementGUI.createTextButton("Hinzufuegen");
        textButtonAdd.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (names.size < 5) {
                    names.add(new UserName("Spieler " + (names.size + 1), new String[] { "Wurm 1", "Wurm 2", "Wurm 3", "Wurm 4", "Wurm 5"}));
                    list.setItems(names);
                }
            }
        });

        textButtonDelete = elementGUI.createTextButton("Entfernen");
        textButtonDelete.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (names.size > 2) {
                    names.removeIndex(list.getSelectedIndex());
                    list.setItems(names);
                    updateTextFields();
                }
            }
        });
        tableTextButtonPlayer = new Table();
        tableTextButtonPlayer.add(textButtonAdd);
        tableTextButtonPlayer.add(textButtonDelete);

        list = new List<UserName>(elementGUI.getSkin());
        list.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateTextFields();
            }
        });

        list.setItems(names);

        scrollPane = new ScrollPane(list, elementGUI.getSkin());
        scrollPane.setBounds(20, 40, 300, 350);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setTransform(true);
        scrollPane.setScale(1f);

        tableTextButtonPlayer.row();
        tableTextButtonPlayer.add(scrollPane).colspan(2).size(300, 200).padTop(20);

        tableBackground.add(tableTextButtonPlayer, tableTextField);
        tableBackground.row();

        Table menuButtonsTable = new Table();
        menuButtonsTable.row().size(200, 60).pad(10);
        menuButtonsTable.add(textButtonMenu, textButtonSpielen);
        tableBackground.add(menuButtonsTable).colspan(2);
        tableBackground.padTop(50);
        stage.addActor(tableBackground);


        elementGUI.setSelectedImageButton(buttonMap1);
        elementGUI.setSelectedImageButton2(buttonWorm1);

        updateTextFields();

//        stage.setDebugAll(true);
    }


    @Override
    public void render(float delta) {
        // clears screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void hide() {
        if (client != null) {
            client.unregisterDataHandler(dataHandler);
        }
        stage.dispose();
    }


}
