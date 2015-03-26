package donnu.zolotarev.wallpaper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/** @author cb ** http://vk.com/id17317 **/

public class WallpaperScreen implements InputProcessor,Screen {

	protected LwpGdx lwp;
	
	private Environment			env_lights;
	private DirectionalLight	light01;
	private PerspectiveCamera	camera;

	private Color bgColor;
	final private Color[] colorPack = {
		// sandy stone beach ocean
		new Color(tf(0xE6), tf(0xE2), tf(0xAF), 1.0f),
		new Color(tf(0xA7), tf(0xA3), tf(0x7E), 1.0f),
		new Color(tf(0xEF), tf(0xEC), tf(0xCA), 1.0f),
		new Color(tf(0x04), tf(0x63), tf(0x80), 1.0f),
		new Color(tf(0x00), tf(0x2F), tf(0x2F), 1.0f),
		// Firenze
		new Color(tf(0x46), tf(0x89), tf(0x66), 1.0f),
		new Color(tf(0xFF), tf(0xF0), tf(0xA5), 1.0f),
		new Color(tf(0xB6), tf(0x49), tf(0x26), 1.0f),
		new Color(tf(0x8E), tf(0x28), tf(0x00), 1.0f),
		// 1944mustang
		new Color(tf(0x00), tf(0x00), tf(0x00), 1.0f),
		new Color(tf(0x26), tf(0x32), tf(0x48), 1.0f),
		new Color(tf(0x7E), tf(0x8A), tf(0xA2), 1.0f),
		new Color(tf(0xFF), tf(0xFF), tf(0xFF), 1.0f),
		new Color(tf(0xFF), tf(0x98), tf(0x00), 1.0f),
	};

	// ingame flags
	private boolean touchedOn_flag = false;
	private boolean touchedOff_flag = false;
	private boolean screen_hided = false;
	private boolean screen_resting = false;

	// data
	private AssetManager		assets;

	// Models
    private ModelInstance		backgroundModel;
    final private String		modelDir = "model/";
    final private String		[]models = {
    		"music.g3dj",
    		"cone1.g3db",
    		"ball.g3db",
    		"land02.g3dj",
    		"plates.g3dj",
    		"tube_cells.g3dj",
    		"heart.g3dj"
    	};
    private int current_model;


    Actor cameraX_Actor;
    Actor cameraY_Actor;
    Actor cameraZ_Actor;
    
    public WallpaperScreen(LwpGdx lwp)
    { 
    	this.lwp = lwp;
    	current_model = lwp.scene;
    	
        env_lights = new Environment();
        light01 = new DirectionalLight().set(0.8f, 0.8f, 0.8f, new Vector3(-1f, -2f, -1.5f));
        configure_sceneLights();


		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // default 67
        camera.position.set(0.0f, 15.0f, 35.0f);
        camera.lookAt(0,10.0f,0);
        camera.near = 0.1f;
        camera.far = 400f;
        camera.update();
        
        assets = new AssetManager(); // start
        //assets.finishLoading(); // end
        
        load_backgroundModel();

        Gdx.input.setInputProcessor(this);
        
        cameraX_Actor = new Actor();
        cameraY_Actor = new Actor();
        cameraZ_Actor = new Actor();
        configure_cameraActors();
        
        
    } // WallpaperScreen - constructor

    
    
    private void configure_bgColor()
    {
    	bgColor = colorPack[lwp.bg_color];
    	
    } // configure_BGColor 
    
    private void configure_sceneLights()
    {
    	configure_bgColor();
    	
    	env_lights.clear();
		if( lwp.bg_fog ) env_lights.set(new ColorAttribute(ColorAttribute.Fog, bgColor.r, bgColor.g, bgColor.b, bgColor.a));
        env_lights.add(light01);

    	
    } // configure_sceneLights
    
    private void configure_cameraActors()
    {
    	cameraX_Actor.clearActions();
    	cameraY_Actor.clearActions();
    	cameraZ_Actor.clearActions();

    	switch(lwp.cam_actors)
    	{
    	case 0: 
        	cameraX_Actor.setPosition(0, 0);
            cameraY_Actor.setPosition(0, 0);
            cameraZ_Actor.setPosition(0, 0);
    		break;
    	case 1: 
        	cameraX_Actor.setPosition(2, 0);
            cameraX_Actor.addAction( Actions.forever(
        			Actions.sequence(
        				Actions.moveTo(0.0f, 0.0f, 3.0f, Interpolation.pow2),
        				Actions.moveTo(2.0f, 0.0f, 3.0f, Interpolation.pow2)
        			)
        		));
            cameraY_Actor.setPosition(2, 0);
            cameraY_Actor.addAction( Actions.forever(
        			Actions.sequence(
        				Actions.moveTo(0.0f, 0.0f, 5.0f, Interpolation.pow2),
        				Actions.moveTo(2.0f, 0.0f, 5.0f, Interpolation.pow2)
        			)
        		));
            cameraZ_Actor.setPosition(3, 0);
            cameraZ_Actor.addAction( Actions.forever(
        			Actions.sequence(
        				Actions.moveTo(0.0f, 0.0f, 7.0f, Interpolation.pow2),
        				Actions.moveTo(3.0f, 0.0f, 7.0f, Interpolation.pow2)
        			)
        		));
    		break;
    	case 2: 
        	cameraX_Actor.setPosition(2, 0);
            cameraX_Actor.addAction( Actions.forever(
        			Actions.sequence(
        				Actions.moveTo(0.0f, 1.0f, 3.0f, Interpolation.elastic),
        				Actions.moveTo(2.0f, 0.0f, 3.0f, Interpolation.elastic)
        			)
        		));
            cameraY_Actor.setPosition(2, 0);
            cameraY_Actor.addAction( Actions.forever(
        			Actions.sequence(
        				Actions.moveTo(0.0f, 0.0f, 5.0f, Interpolation.elastic),
        				Actions.moveTo(2.0f, 0.0f, 5.0f, Interpolation.elastic)
        			)
        		));
            cameraZ_Actor.setPosition(3, 0);
            cameraZ_Actor.addAction( Actions.forever(
        			Actions.sequence(
        				Actions.moveTo(0.0f, 0.0f, 7.0f, Interpolation.elastic),
        				Actions.moveTo(3.0f, 0.0f, 7.0f, Interpolation.elastic)
        			)
        		));
    		break;
    	} // switch(game.cam_actors)
    	
    } // configure_cameraActors




	private void load_backgroundModel() {
		if(current_model != lwp.scene)
		{
			final String modelPath = modelDir + models[current_model];
			assets.unload(modelPath);
			backgroundModel = null;
		}

		current_model = lwp.scene;
		
		if(backgroundModel == null )
		{
			final String modelPath = modelDir + models[current_model];
			assets.load(modelPath, Model.class);
	        assets.finishLoading();
			backgroundModel = getModelInstance( modelPath ); 
		}
		
		configure_ModelMaterial();

	} // update_backgroundModel


    private void configure_ModelMaterial()
    {
    	backgroundModel.materials.get(0).clear();
			
		if(lwp.scene_spec)
			backgroundModel.materials.get(0).set(new ColorAttribute(ColorAttribute.Diffuse, colorPack[lwp.scene_color]), ColorAttribute.createSpecular(Color.WHITE), new BlendingAttribute(0.3f));
		else
			backgroundModel.materials.get(0).set(new ColorAttribute(ColorAttribute.Diffuse, colorPack[lwp.scene_color]), ColorAttribute.createSpecular(Color.BLACK));

		
    	
    } // configure_ModelMaterial 



	
    @Override
    public void render(float delta) {
    	/** 
    	 * ����� �� ���� ������� � ������������, ����� �� ������ ���� ���� ������� 
    	 * 
    	 * ���� �� ��������, ��� ���������� ���������, �� ������������� ��� ������.
    	 * ��� �� ����� ������������ - �� �� ���������������
    	 * 
    	 * */
        if(lwp.settings_changed_flag) {
        	configure_sceneLights();
        	configure_cameraActors();
        	load_backgroundModel();
            lwp.settings_changed_flag = false; // ���������� ����
		}
    	
    	/** 
    	 * ������� ��� ���������
    	 * 
    	 * */
    	if(!screen_hided && !screen_resting && !lwp.settings_changed_flag)
    	{
    		cameraX_Actor.act(delta);
    		cameraY_Actor.act(delta);
    		cameraZ_Actor.act(delta);
	        // ���� ��������� ������
	        if(touchedOn_flag) {
	        }

	        // ���� ������� ����� � ������ ������
	        if(touchedOff_flag) {
	        	//change_targetAnimation();
	        	touchedOff_flag = false; // ���������� ����
	        }
    		
	        Gdx.gl.glViewport( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
			Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
			Gdx.gl.glClearColor( bgColor.r, bgColor.g, bgColor.b, bgColor.a );
			//Gdx.gl.glEnable(GL20.GL_CULL_FACE); 
			//Gdx.gl.glCullFace(GL20.GL_BACK);
			//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

			
			//game.screenOffset = cameraX_Actor.getY();
			float d = lwp.screenOffset - 0.5f;
			if(d < 0.0f) d *= -1.0f;
	        
	        camera.position.set(0 + 2.0f - lwp.screenOffset * 4.0f + cameraX_Actor.getX(), 0.0f + cameraY_Actor.getX(), 305.0f - d * 4 - cameraZ_Actor.getX());
	        camera.lookAt(0.0f - 5.0f + lwp.screenOffset * 10.0f + cameraX_Actor.getX(), 0.0f + cameraY_Actor.getX(), 0.0f - d * 50 - cameraZ_Actor.getX());
	        camera.update();
	        
	        

        	//if(game.road_mat != 1)backgroundModelAnimation.update( delta );
    		light01.direction.rotate(Vector3.Y, delta * 27f);
    		backgroundModel.transform.rotate(Vector3.Y, delta * 1.7f);
				
		    //if(game.hero != 1) heroModelAnimation.update( delta );


    	}
    } // render


    
    /** �����-�������� ��� ���������� �������� ������ */
	public ModelInstance getModelInstance( final String name )
	{ 
		return new ModelInstance( assets.get(name, Model.class) );
	}

	
	
    /** �����-�������� ��� ���������� �������� 1 ��������� �� ����� ������ */
	public Material getMaterial( final String name )
	{
		return assets.get(name, Model.class).materials.get(0);
	}

	final float tf( int a ) { return (float)((float)a / (float)0xFF); }
    
    @Override
    public void show() {
    	screen_hided = false;
		screen_resting = false;
    }
    
    @Override
    public void resize(int width, int height) {
		camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void dispose()
    {

    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int newParam) {
    	touchedOn_flag = true; // ������������� ����
    	return false; 
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
    	touchedOn_flag = false; // ���������� ����
    	touchedOff_flag = true; // ������������� ����
    	return false; 
    }

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

	@Override
	public boolean mouseMoved(int screenX, int screenY) { return false; }

	@Override
	public boolean scrolled(int amount) { return false; }

    @Override
    public boolean keyUp (int keycode) { return false; }
    
    @Override
    public boolean keyDown (int keycode) { return false; }

    @Override
    public boolean keyTyped (char character) { return false; }

	@Override
	public void hide() { screen_hided = true; }

	@Override
	public void pause() { screen_resting = true; }

	@Override
	public void resume() { screen_hided = false; screen_resting = false; }

}
