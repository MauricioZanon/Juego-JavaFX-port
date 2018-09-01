package main;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import RNG.RNGTests;
import entities.ComponentsTests;
import entities.EntityTests;
import entities.FactoriesTests;
import map.ChunkTests;
import map.MapTests;
import map.TileTests;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	MapTests.class,
	ChunkTests.class,
	TileTests.class,
	
	FactoriesTests.class,
	EntityTests.class,
	ComponentsTests.class,
	
	RNGTests.class,
})

public class TestsSuite {}
