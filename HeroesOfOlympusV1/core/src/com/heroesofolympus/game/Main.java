package com.heroesofolympus.game;

import java.rmi.server.Skeleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main extends Game implements Screen {

	// Set screen size
	public static final int WIDTH = 1050;
	public static final int HEIGHT = 800;

	volatile int counter = 0;

	// This variable controls/switches the attack
	volatile boolean check = false;
	volatile boolean achilleswitch = false;
	volatile boolean herculesswitch = false;
	volatile boolean hypolytaswitch = false;
	volatile boolean thesiusswitch = false;

	float helencurrentstartTime = 0;
	private Enemy titan;
	private float currentstartTime = 0;
	private float achillecurrentstartTime = 0;
	// this variable define when to load the titan
	volatile int checkaction = 0;

	SpriteBatch batch;
	Texture achille_img, helen_img, hercules_img, hypolyta_img, thesius_img, skeleton_img, skeletonArmy1, skeletonArmy2,
			skeletonArmy3, skeletonArmy4, skeletonArmy5;
	Sprite achilleStartPosition, helenStartPosition, herculesStartPosition, hypolytaStartPosition, thesiusStartPosition,
			skeletonStartPosition, skeleton1StartPosition, skeleton2StartPosition, skeleton3StartPosition,
			skeleton4StartPosition, skeleton5StartPosition;

	/* GameScreen game_screen; */
	GameMap gameMap;

	// general Sprite handle
	Sprite sprite;
	Hero achille;
	Hero conthypo;
	SpriteBatch spriteBatch;

	// A variable for tracking elapsed time for the animation
	float stateTime, tracker;

	// Check if selected dialog option is shown
	boolean optionsShow = false;

	OrthographicCamera camera;
	// Hero achille;
	Hero helen;
	Hero hercules;
	Hero hypolyta;
	Hero thesius;
	ArrayList<Enemy> skeleton;

	Vector3 touch;
	Dialog achilleOptions, achilleMove, helenOptions, helenMove, herculesOptions, herculesMove, hypolytaOptions,
			hypolytaMoves, thesiusOptions, thesiusMove;
	Stage stage;
	Skin skin;
	Viewport viewport;

	// An ArrayList used to store/check whether the player has had their turn or not
	ArrayList<String> playerTurn;

	private TextureRegion holder;
	private Achille achilleAdvan;
	private Helen helenAdvan;
	private Hercules herculesAdvan;
	private Hippolyta hypolytaAdvan;
	private Theseus thesiusAdvan;
	private Weapons thesiusweapon;
	private Weapons weapon;
	private Weapons weaponAdvan;

	private float[] location = new float[2];
	private volatile boolean heartswitch2 = false;
	private Hero generalHero;
	private Texture general_img;
	private Sprite generalStartPosition;
	private Texture titan_img;
	private Sprite titanStartPosition;
	Achille titanAdva;

	private int escape = 1;

	@Override
	public void create() {
		skeleton = new ArrayList();
		batch = new SpriteBatch();

		sprite = new Sprite();
		generalHero = new Hero(sprite, null);
		achilleAdvan = new Achille(sprite, null);
		helenAdvan = new Helen(sprite, null);
		herculesAdvan = new Hercules(sprite, null);
		hypolytaAdvan = new Hippolyta(sprite, null);
		thesiusAdvan = new Theseus(sprite, null);
		weaponAdvan = new Weapons(sprite, null);
		titanAdva = new Achille(sprite, null);
		new Animator();
		new Sprite();
		// Animation animation = new Animation();

		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1280, 1080);
		touch = new Vector3();
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("default/skin/uiskin.json"));
		playerTurn = new ArrayList<>();

		// initializing animation variables.
		spriteBatch = new SpriteBatch();
		stateTime = 0f;

		// Achille Dialog Menu Box
		achilleOptions = new Dialog("Achille", skin) {
			protected void result(Object object) {
				if (object.equals(1)) {
					// Set dialog state to false when checked on
					System.out.println("this swiches on the attack");
					achilleswitch = true;
					optionsShow = false;
					checkaction++;
				} else if (object.equals(2)) {
					// New Dialog box to enable specific movement for player
					achilleMove = new Dialog("Move", skin) {
						protected void result(Object object) {
							// Move Up
							if (object.equals(1)) {
								achille.setPosition(achille.getX() + 0, achille.getY() - 64);
							}
							// Move Down
							else if (object.equals(2)) {
								achille.setPosition(achille.getX() + 0, achille.getY() + 64);

							}
							// Move Left
							else if (object.equals(3)) {
								achille.setPosition(achille.getX() - 64, achille.getY() + 0);
							}
							// Move Right
							else {
								achille.setPosition(achille.getX() + 64, achille.getY() + 0);
							}
							optionsShow = false;
						}
					}

							.button("Up", 1).button("Down", 2).button("Left", 3).button("Right", 4).show(stage);

					// System.out.println("Button Pressed");

				} else {
					optionsShow = false;
				}
			};
		};

		achilleOptions.button("Attack", 1);
		achilleOptions.button("Move", 2);
		achilleOptions.button("Stay", 3);

		// Helen Dialog Menu Box
		helenOptions = new Dialog("Helen", skin) {
			protected void result(Object object) {
				if (object.equals(1)) {
					check = true;
					optionsShow = false;
					checkaction++;
				} else if (object.equals(2)) {
					helenMove = new Dialog("Move", skin) {
						protected void result(Object object) {
							// Move Up
							if (object.equals(1)) {
								helen.setPosition(helen.getX() + 0, helen.getY() - 64);
							} else if (object.equals(2)) {
								helen.setPosition(helen.getX() + 0, helen.getY() + 64);

							} else if (object.equals(3)) {
								System.out.println("Hellen id Healing");
								// helen.setPosition(helen.getX()-64, helen.getY()+0);
								render(stateTime / (float) Math.acos(60));
							} else {
								helen.setPosition(helen.getX() + 64, helen.getY() + 0);
							}
							optionsShow = false;
						}
					}

							.button("Up", 1).button("Down", 2).button("Left", 3).button("Right", 4).show(stage);
					// System.out.println("Button Pressed");

				} else {
					optionsShow = false;
				}

			};
		};

		helenOptions.button("Attack", 1);
		helenOptions.button("Move", 2);
		helenOptions.button("Stay", 3);

		// Hercules Dialog Menu Box
		herculesOptions = new Dialog("Hercules", skin) {
			protected void result(Object object) {
				if (object.equals(1)) {
					herculesswitch = true;
					optionsShow = false;
					checkaction++;
				} else if (object.equals(2)) {
					herculesMove = new Dialog("Move", skin) {
						protected void result(Object object) {
							// Move Up
							if (object.equals(1)) {
								hercules.setPosition(hercules.getX() + 0, hercules.getY() - 64);
							} else if (object.equals(2)) {
								hercules.setPosition(hercules.getX() + 0, hercules.getY() + 64);

							} else if (object.equals(3)) {
								hercules.setPosition(hercules.getX() - 64, hercules.getY() + 0);
							} else {
								hercules.setPosition(hercules.getX() + 64, hercules.getY() + 0);
							}
							optionsShow = false;
						}
					}

							.button("Up", 1).button("Down", 2).button("Left", 3).button("Right", 4).show(stage);
					System.out.println("Button Pressed");

				} else {
					optionsShow = false;
				}
			};
		};

		herculesOptions.button("Attack", 1);
		herculesOptions.button("Move", 2);
		herculesOptions.button("Stay", 3);

		// Hypolyta Dialog Menu Box
		hypolytaOptions = new Dialog("Hypolyta", skin) {
			protected void result(Object object) {
				if (object.equals(1)) {
					// Set dialog state to false when checked on
					hypolytaswitch = true;
					optionsShow = false;
					checkaction++;
				} else if (object.equals(2)) {
					// New Dialog box to enable specific movement for player
					hypolytaMoves = new Dialog("Move", skin) {
						protected void result(Object object) {
							// Move Up
							if (object.equals(1)) {
								hypolyta.setPosition(hypolyta.getX() + 0, hypolyta.getY() - 64);
							}
							// Move Down
							else if (object.equals(2)) {
								hypolyta.setPosition(hypolyta.getX() + 0, hypolyta.getY() + 64);

							}
							// Move Left
							else if (object.equals(3)) {
								hypolyta.setPosition(hypolyta.getX() - 64, hypolyta.getY() + 0);
							}
							// Move Right
							else {
								hypolyta.setPosition(hypolyta.getX() + 64, hypolyta.getY() + 0);
							}
							optionsShow = false;
						}
					}

							.button("Up", 1).button("Down", 2).button("Left", 3).button("Right", 4).show(stage);

					System.out.println("Button Pressed");

				} else {
					optionsShow = false;
				}
			};
		};

		hypolytaOptions.button("Attack", 1);
		hypolytaOptions.button("Move", 2);
		hypolytaOptions.button("Stay", 3);

		// thesius Dialog Menu Box
		thesiusOptions = new Dialog("Thesius", skin) {
			protected void result(Object object) {
				if (object.equals(1)) {
					// Set dialog state to false when checked on
					thesiusswitch = true;
					optionsShow = false;
					checkaction++;
				} else if (object.equals(2)) {
					// New Dialog box to enable specific movement for player
					thesiusMove = new Dialog("Move", skin) {
						protected void result(Object object) {
							// Move Up
							if (object.equals(1)) {
								thesius.setPosition(thesius.getX() + 0, thesius.getY() - 64);
							}
							// Move Down
							else if (object.equals(2)) {
								thesius.setPosition(thesius.getX() + 0, thesius.getY() + 64);

							}
							// Move Left
							else if (object.equals(3)) {
								thesius.setPosition(thesius.getX() - 64, thesius.getY() + 0);
							}
							// Move Right
							else {
								thesius.setPosition(thesius.getX() + 64, thesius.getY() + 0);
							}
							optionsShow = false;
						}
					}

							.button("Up", 1).button("Down", 2).button("Left", 3).button("Right", 4).show(stage);

					System.out.println("Button Pressed");

				} else {
					optionsShow = false;

				}
			};
		};

		thesiusOptions.button("Attack", 1);
		thesiusOptions.button("Move", 2);
		thesiusOptions.button("Stay", 3);

		gameMap = new TiledGameMap();
		Gdx.input.setInputProcessor(stage);

		/******************** Heroes *****************************/

		// Reading achille image file
		achille_img = new Texture(Gdx.files.internal("Achilles.png"));

		// Get the specific sprite from the sprite sheet - pass in achilles image,
		// read from top left of sprite sheet, X-axis = 0 and from the y-axis we add 64
		// each time to get the sprite. 64 by 64 is done to only get the width & height
		// of that single sprite.

		holder = achilleAdvan.getAchilleFrames()[0];
		achille_img = holder.getTexture();
		achilleStartPosition = new Sprite(achille_img, 0, 64, 64, 64);
		achille = new Hero(achilleStartPosition, null);
		achilleStartPosition.flip(true, true);
		achille.setPosition(256, 128);

		holder = helenAdvan.getAchilleFrames()[0];
		helen_img = holder.getTexture(); // new Texture(Gdx.files.internal("Helen.png"));
		// helenStartPosition = new Sprite(helen_img,0,128,64,64);
		helenStartPosition = new Sprite(helen_img, 0, 192, 64, 64);
		// helenStartPosition.setTexture( achille.getAchilleFrames()[0].getTexture());
		helenStartPosition.flip(false, true);
		helen = new Hero(helenStartPosition, null);
		helen.setPosition(128, 64);

		// hercules_img= new Texture(Gdx.files.internal("Hercules.png")); done
		holder = herculesAdvan.getAchilleFrames()[0];
		hercules_img = holder.getTexture();
		herculesStartPosition = new Sprite(hercules_img, 128, 192, 64, 64);
		herculesStartPosition.flip(false, true);
		hercules = new Hero(herculesStartPosition, null);
		hercules.setPosition(256, 320);

		holder = hypolytaAdvan.getAchilleFrames()[0]; // done
		hypolyta_img = holder.getTexture();
		// hypolyta_img= new Texture(Gdx.files.internal("Hypolyta.png"));
		hypolytaStartPosition = new Sprite(hypolyta_img, 0, 192, 64, 64);
		hypolytaStartPosition.flip(false, true);
		hypolyta = new Hero(hypolytaStartPosition, null);
		hypolyta.setPosition(128, 320);
		conthypo = hypolyta;

		holder = thesiusAdvan.getAchilleFrames()[0];
		thesius_img = holder.getTexture();
		// thesius_img= new Texture(Gdx.files.internal("Thesius.png"));
		thesiusStartPosition = new Sprite(thesius_img, 0, 192, 64, 64);
		thesiusStartPosition.flip(false, true);
		thesius = new Hero(thesiusStartPosition, null);
		thesius.setPosition(128, 512);

		holder = thesiusAdvan.getAchilleFrames()[0];
		general_img = holder.getTexture();
		// thesius_img= new Texture(Gdx.files.internal("Thesius.png"));
		generalStartPosition = new Sprite(general_img, 64, 256, 64, 64);
		generalStartPosition.flip(false, true);
		generalHero = new Hero(generalStartPosition, null);
		generalHero.setPosition(128, 512);

		///////////////// titan ///////////////////////

		holder = titanAdva.getAchilleFrames()[0];
		titan_img = holder.getTexture();
		titanStartPosition = new Sprite((new Enemy(sprite, null)).getBlankHeartRegion());
		titanStartPosition.flip(false, true);
		titan = new Enemy(titanStartPosition, null);
		titan.setPosition(500, 400);

		/*
		 * this is a dammy weapon holder = weaponAdvan.getWeaponFrames()[0]; weapon_img=
		 * holder.getTexture(); //thesius_img= new
		 * Texture(Gdx.files.internal("Thesius.png")); weaponStartPosition = new
		 * Sprite(weapon_img,0,0,160,400); weaponStartPosition.flip(false, true);
		 * weaponAdvan = new Weapons(weaponStartPosition, null);
		 * weaponAdvan.setPosition(128, 512);
		 */

		/********************** Enemies *********************/

		
		skeleton_img = new Texture(Gdx.files.internal("skeletonSpear.png"));
		skeletonStartPosition = new Sprite(skeleton_img, 0, 320, 64, 64);
		skeletonStartPosition.flip(false, true);
		Enemy e1 = new Enemy(skeletonStartPosition, null);
		e1.setPosition(832, 320);
		skeleton.add(e1);
		

		skeletonArmy1 = new Texture(Gdx.files.internal("skeletonSpear.png"));
		skeleton1StartPosition = new Sprite(skeletonArmy1, 64, 320, 64, 64);
		skeleton1StartPosition.flip(false, true);
		
		Enemy e2 = new Enemy(skeleton1StartPosition, null);
		e2.setPosition(768, 384);
		skeleton.add(e2);

		skeletonArmy2 = new Texture(Gdx.files.internal("skeletonSpear.png"));
		skeleton2StartPosition = new Sprite(skeletonArmy2, 128, 320, 64, 64);
		skeleton2StartPosition.flip(false, true);
		Enemy e3 = new Enemy(skeleton2StartPosition, null);
		e3.setPosition(704, 448);
		skeleton.add(e2);
		
		skeletonArmy3 = new Texture(Gdx.files.internal("skeletonSpear.png"));
		skeleton3StartPosition = new Sprite(skeletonArmy3, 192, 320, 64, 64);
		skeleton3StartPosition.flip(false, true);
		Enemy e4 = new Enemy(skeleton3StartPosition, null);
		e4.setPosition(640, 512);
		skeleton.add(e4);
		
		skeletonArmy4 = new Texture(Gdx.files.internal("skeletonSpear.png"));
		skeleton4StartPosition = new Sprite(skeletonArmy4, 256, 320, 64, 64);
		skeleton4StartPosition.flip(false, true);
		Enemy e5 =  new Enemy(skeleton4StartPosition, null);
		e5.setPosition(448, 576);
		skeleton.add(e5);

	}

	@Override
	public void render() {
		/* update(); */

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		batch.setProjectionMatrix(camera.combined);
		gameMap.render(camera);
		camera.update();

		batch.begin();

		// render Achille

		achille.draw(batch);
		helen.draw(batch);
		hercules.draw(batch);
		hypolyta.draw(batch);
		thesius.draw(batch);

		for (int i = 0; i < skeleton.size(); i++) {
			skeleton.get(i).draw(batch);
		}

		titan.draw(batch);

		if (check) {
		
			int skeleToKill = getClosest(helen);
			enemyFalls(skeleton.get(skeleToKill)).draw(batch);
			render(Gdx.graphics.getDeltaTime(), batch, new Helen(sprite, null), skeleToKill);
			

			
			
		}
		//
		if (achilleswitch) {
			int skeleToKill = getClosest(achille);
			enemyFalls(skeleton.get(skeleToKill)).draw(batch);
			achilleRender(Gdx.graphics.getDeltaTime(), batch, new Achille(sprite, null), skeleToKill);

			
			
			
			
			//achilleswitch=false;
		}
		// herculesswitch
		if (herculesswitch == true) {
			int skeleToKill = getClosest(hercules);
			enemyFalls(skeleton.get(skeleToKill)).draw(batch);
			herculesRender(Gdx.graphics.getDeltaTime(), batch, new Hercules(sprite, null), skeleToKill);
			
			
		}
		// hypolytasswitch
		if (hypolytaswitch == true) {
			int skeleToKill = getClosest(hypolyta);
			hippolytaRender(Gdx.graphics.getDeltaTime(), batch, new Hippolyta(sprite, null), skeleToKill);
			enemyFalls(skeleton.get(skeleToKill)).draw(batch);
		
		}
		// theseus switched on.
		if (thesiusswitch == true) {
			int skeleToKill = getClosest(thesius);
			theseusRender(Gdx.graphics.getDeltaTime(), batch, new Theseus(sprite, null), skeleToKill);
			enemyFalls(skeleton.get(skeleToKill)).draw(batch);
			
		}

		///// check if all the heroes have attacked and all the enemies dead
		if (checkaction == 5 && escape == 1) {
			// loadTitan().draw(batch);
			Sprite trick = new Hero(sprite, null).titanSprite();
			titan = new Enemy(trick, null);
			titan.setPosition(500, 400);

		}

		if (checkaction == 5) {
			escape = 2;
		}

		batch.end();

		stage.act();
		stage.draw();

		// When the player/hero is clicked on
		if (Gdx.input.isTouched()) {

			System.out.println("Hero has been touched");
			touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touch);

			// This will check if the current options/dialog box are displayed or not and if
			// the ArrayList contains/counted 'Achille'
			// Get the X and Y of the sprite when clicked on
			if (!optionsShow && !playerTurn.contains("achille") && touch.x >= achille.getX()
					&& touch.x <= achille.getX() + 64 && touch.y >= achille.getY() && touch.y <= achille.getY() + 64) {

				// Display Main dialog box
				achilleOptions.show(stage);

				// If 'Achille' has not yet had a turn, then add it to the count in the
				// ArrayList
				playerTurn.add("achille");

				// If no other hero/player options are currently shown it will display the
				// dialog box
				optionsShow = true;

			}
			if (!optionsShow && !playerTurn.contains("helen") && touch.x >= helen.getX() && touch.x <= helen.getX() + 64
					&& touch.y >= helen.getY() && touch.y <= helen.getY() + 64) {

				helenOptions.show(stage);
				playerTurn.add("helen");
				optionsShow = true;
			}
			if (!optionsShow && !playerTurn.contains("hercules") && touch.x >= hercules.getX()
					&& touch.x <= hercules.getX() + 64 && touch.y >= hercules.getY()
					&& touch.y <= hercules.getY() + 64) {

				herculesOptions.show(stage);
				playerTurn.add("hercules");
				optionsShow = true;
			}
			if (!optionsShow && !playerTurn.contains("hypolyta") && touch.x >= hypolyta.getX()
					&& touch.x <= hypolyta.getX() + 64 && touch.y >= hypolyta.getY()
					&& touch.y <= hypolyta.getY() + 64) {

				hypolytaOptions.show(stage);
				playerTurn.add("hypolyta");
				optionsShow = true;
			}
			if (!optionsShow && !playerTurn.contains("thesius") && touch.x >= thesius.getX()
					&& touch.x <= thesius.getX() + 64 && touch.y >= thesius.getY() && touch.y <= thesius.getY() + 64) {

				thesiusOptions.show(stage);
				playerTurn.add("thesius");
				optionsShow = true;
			}

			// Here we check or alternatively set the count/size of the ArrayList to be 5
			// because we have 5 heroes.
			if (playerTurn.size() == 5) {
				playerTurn = new ArrayList<>();
			}

		}

	}
	
	public int getClosest(Hero hero) {

		int smallest = 0;
		float[] distances = new float[skeleton.size()];
		for (int i = 0; i < skeleton.size(); i++) {
				float distance = (skeleton.get(i).getX() - hero.getX()) + (skeleton.get(i).getY() - hero.getY());
				distances[i] = distance;					
				Arrays.sort(distances);
				//float smallestDistance = (skeleton.get(smallest).getX() - hero.getX()) + (skeleton.get(smallest).getY() - hero.getY());
				for(int j=0; j < distances.length; j++) {
					distances[j] = smallest;
					
				}
			
		}

	return smallest;

	}

	/*********************
	 * * CHECK WHETHER TWO SPRITES ARE ON THE SAME TILE
	 *********************/
	/*
	 * public boolean hitTest(Hero achille, Hero helen) {
	 * 
	 * if(achille.getX() < helen.getX() + helen.getWidth() && achille.getX() +
	 * achille.getWidth() > helen.getWidth() && achille.getY() < helen.getY() +
	 * helen.getHeight() && achille.getY() + achille.getHeight() > helen.getY()) {
	 * return true; }
	 * 
	 * return false; }
	 * 
	 * public void update() {
	 * 
	 * if(hitTest(achille, helen)) { spriteShow = false; } }
	 */

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();

		spriteBatch.dispose();

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	// helen renderer
	public void render(float delta, SpriteBatch sb, Helen helen2, int skeleToKill) {
		Hero cont = helen;

		helencurrentstartTime += delta;
		// if( helencurrentstartTime < 0.8) {
		Sprite trick = new Sprite((new Hero(sprite, null)).getBlankHeartRegion());

		helen = new Hero(trick, null);
		helen.setPosition(cont.getX(), cont.getY());

		TextureRegion tregion = helen2.animatehyplyta().getKeyFrame(helencurrentstartTime, false);
		TextureRegion herolife = helen2.animateHalftoFull().getKeyFrame(helencurrentstartTime, false);
		TextureRegion enemylife = helen2.animateFulltoHalf().getKeyFrame(helencurrentstartTime, false);

		sb.draw(tregion, cont.getX(), cont.getY()); // skeleton
		sb.draw(herolife, thesius.getX() - 32, thesius.getY() - 32);
		sb.draw(enemylife, skeleton.get(skeleToKill).getX() - 32, skeleton.get(skeleToKill).getY() - 32);
		// }

		// System.out.println("am inside helen animation");
		if (helen2.animatehyplyta().isAnimationFinished(helencurrentstartTime)) {
				check = false;
			  Sprite trick2 = (new Helen(sprite, null)).NormalLook();
				 helen= new Hero(trick2, null);
				 helen.setPosition(cont.getX(), cont.getY());
				 
				skeleton.remove(skeleToKill);
	//			 Enemy enemy = skeleton.get(skeleToKill);
			//	 skeleton = new Enemy(enemyFalls(enemy), null);
			//	 skeleton.setPosition(enemy.getX(), enemy.getY());
		}
	}

	float val = 0;
	private float herculescurrentstartTime = 0;
	private float thesiuscurrentstartTime = 0;

	// Achille's Animation Renderer. Enables "achilleswitch"
	public void achilleRender(float delta, SpriteBatch sb, Achille achille2, int skeleToKill) {

		Hero cont = achille;
		achillecurrentstartTime += delta;
		Sprite trick = new Sprite((new Hero(sprite, null)).getBlankHeartRegion());
		achille = new Hero(trick, null);
		achille.setPosition(cont.getX(), cont.getY());

		TextureRegion tregion = achille2.animatehyplyta().getKeyFrame(achillecurrentstartTime, false);
		TextureRegion enemylife = achille2.animateHalftoEmpty().getKeyFrame(achillecurrentstartTime, false);

		sb.draw(tregion, cont.getX(), cont.getY());
		sb.draw(enemylife, skeleton.get(skeleToKill).getX() - 32, skeleton.get(skeleToKill).getY() - 32);
		// }
		//
		if (achille2.animatehyplyta().isAnimationFinished(achillecurrentstartTime)) {

			achilleswitch = false;
			Sprite trick2 = (new Achille(sprite, null)).NormalLook();
			achille = new Hero(trick2, null);
			achille.setPosition(cont.getX(), cont.getY());
			
			skeleton.remove(skeleToKill);
				
			
		}

	}

	// hercules's animation renderer. its switch is "herculesswitch"
	public void herculesRender(float delta, SpriteBatch sb, Hercules hercules2, int skeleToKill) {
		Hero cont = hercules;

		herculescurrentstartTime += delta;

		Sprite trick = new Sprite((new Hercules(sprite, null)).getBlankHeartRegion());
		hercules = new Hero(trick, null);
		hercules.setPosition(cont.getX(), cont.getY());

		TextureRegion tregion = hercules2.animatehyplyta().getKeyFrame(herculescurrentstartTime, false);
		TextureRegion enemylife = hercules2.animateFulltoHalf().getKeyFrame(herculescurrentstartTime, false);

		sb.draw(tregion, cont.getX(), cont.getY());
		sb.draw(enemylife, skeleton.get(skeleToKill).getX() - 32, skeleton.get(skeleToKill).getY() - 32);
		// }
		if (hercules2.animatehyplyta().isAnimationFinished(herculescurrentstartTime)) {
			herculesswitch = false;
			Sprite trick2 = (new Hercules(sprite, null)).NormalLook();
			hercules = new Hero(trick2, null);
			hercules.setPosition(cont.getX(), cont.getY());

			skeleton.remove(skeleToKill);
			
		}
		
	}

	// hypolyta's animation rendere. its switch is "hypolytaswitch"
	public void hippolytaRender(float delta, SpriteBatch sb, Hippolyta hypo, int skeleToKill) {
		currentstartTime += delta;
		Hero cont = hypolyta;

		Sprite trick = new Sprite((new Hippolyta(sprite, null)).getBlankHeartRegion());
		hypolyta = new Hero(trick, null);
		hypolyta.setPosition(cont.getX(), cont.getY());

		TextureRegion tregion = hypo.animatehyplyta().getKeyFrame(currentstartTime, false);
		TextureRegion enemylife = hypo.animateHalftoEmpty().getKeyFrame(currentstartTime, false);

		sb.draw(tregion, cont.getX(), cont.getY());
		sb.draw(enemylife, skeleton.get(skeleToKill).getX() - 32, skeleton.get(skeleToKill).getY() - 32);
		// }
		if (hypo.animatehyplyta().isAnimationFinished(currentstartTime)) {
			hypolytaswitch = false;
			Sprite trick2 = (new Hippolyta(sprite, null)).NormalLook();
			hypolyta = new Hero(trick2, null);
			hypolyta.setPosition(cont.getX(), cont.getY());

			skeleton.remove(skeleToKill);
		}

	}

	// thesius's animation rendere. its switch is "thesiusswitch"
	public void theseusRender(float delta, SpriteBatch sb, Theseus thesius2, int skeleToKill) {
		Hero cont = thesius;
		thesiuscurrentstartTime += delta;

		Sprite trick = new Sprite((new Theseus(sprite, null)).getBlankHeartRegion());
		thesius = new Hero(trick, null);
		thesius.setPosition(cont.getX(), cont.getY());

		TextureRegion tregion = thesius2.animatehyplyta().getKeyFrame(thesiuscurrentstartTime, false);
		TextureRegion enemylife = thesius2.animateHalftoEmpty().getKeyFrame(thesiuscurrentstartTime, false);

		sb.draw(tregion, cont.getX(), cont.getY());
		sb.draw(enemylife, skeleton.get(skeleToKill).getX() - 32, skeleton.get(skeleToKill).getY() - 32);
		// }
		if (thesius2.animatehyplyta().isAnimationFinished(thesiuscurrentstartTime)) {
			thesiusswitch = false;
			Sprite trick2 = (new Theseus(sprite, null)).NormalLook();
			thesius = new Hero(trick2, null);
			thesius.setPosition(cont.getX(), cont.getY());

			skeleton.remove(skeleToKill);
		}

	}

	// thesius attack
	public void theseusAdvanAttack(float delta) {

		// thesiusweapon.thesiusweapon()
		Animator animateval = new Animator();

		weaponAdvan = new Weapons(thesiusweapon.thesiusweapon(), null);
		location = animateval.render(delta);

		weaponAdvan.setPosition(weapon.getX() + location[0], weapon.getY() + location[1]);
		System.out.println();

	}

	public Sprite enemyFalls(Sprite sp) {
		GameUnit heart;
		heart = new Hero(sprite, null);
		Sprite fullheart = heart.enamyfall();
		fullheart.setPosition(sp.getX(), sp.getY());
		return fullheart;
	}

	@Override
	public void hide() {

		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

	}

}
