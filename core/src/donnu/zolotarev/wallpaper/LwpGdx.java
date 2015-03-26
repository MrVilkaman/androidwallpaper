package donnu.zolotarev.wallpaper;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/** @author cb ** http://vk.com/id17317 **/

public class LwpGdx extends Game implements ApplicationListener {
	public float screenOffset;
	
	private boolean paused;
	private WallpaperScreen screen;
	
	
	public boolean		settings_changed_flag = false;

	/*
	public int			scene;
	public int			cam_actors;
	public int			scene_color;
	public int			bg_color;
	public boolean		scene_spec;
	public boolean		bg_fog;
	*/

	// DESKTOP DEBUG
	
	// Это только для отладки дэск-топ проекта. Когда приложение будет готово, эту часть нужно
	// закомментировать. А часть выше - раскомментировать.
	
	
	public int			scene = 		2;
	public int			cam_actors =	2; // 0=off 1=smooth 2=real
	public int			scene_color =	12;
	public int			bg_color =		4;
	public boolean		scene_spec =	false;
	public boolean		bg_fog =		true;
	


	
	@Override
	public void create() {
		screenOffset = 0.5f; // центральный рабочий стол телефона (относительно самого левого и самого правого рабочих столов)
							// левый стол = 0.0f
							// правый стол = 1.0f
		paused = false;
		screen = new WallpaperScreen( this );
		setScreen( screen );
		
	} // create

	@Override
	public void dispose() { screen.dispose(); }

	@Override
	public void render() { if(!paused) screen.render( Gdx.graphics.getDeltaTime() ); }

	@Override
	public void resize(int width, int height) { screen.resize( width, height ); }

	@Override
	public void pause() { paused = true; }

	@Override
	public void resume() { paused = false; }
}
