package main;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import RNG.NoiseTest;
import RNG.RNGTests;
import actions.ActionsTests;
import application.Main;
import entities.ComponentsTests;
import entities.EntityTests;
import entities.FactoriesTests;
import eventSystem.EventSystemTests;
import factories.EntityFactory;
import map.BlueprintTests;
import map.ChunkTests;
import map.MapTests;
import map.RoomFactoryTests;
import map.TileTests;
import player.PlayerBuilder;
import world.WorldBuilder;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	RNGTests.class,
	NoiseTest.class,
	
	FactoriesTests.class,
	EntityTests.class,
	ComponentsTests.class,
	
	ActionsTests.class,
	
	MapTests.class,
	ChunkTests.class,
	TileTests.class,
	
	RoomFactoryTests.class,
	BlueprintTests.class,
	
	EventSystemTests.class,
})

public class TestsSuite {
	@ClassRule public static JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	@BeforeClass
	public static void initializeTestEnviroment() {
		EntityFactory.loadEntities();
		WorldBuilder.createWorld();
		Main.player = PlayerBuilder.createBasePlayer();
	}
}

/**
 * FIXME: Cuando se corre el suit las tests que usan @RepeatedTest no se repiten
 * TODO: Tests del dungeon
 * TODO: Tests de la cueva
 */
