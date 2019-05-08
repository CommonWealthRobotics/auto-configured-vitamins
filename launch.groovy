// load the robot
def base =DeviceManager.getSpecificDevice( "HephaestusArm",{
	return ScriptingEngine.gitScriptRun(
            "https://github.com/madhephaestus/SeriesElasticActuator.git", // git location of the library
            "FullHardwareLaunch.groovy" , // file to load
            null
            )
})
