// load the robot
def base =DeviceManager.getSpecificDevice( "HephaestusArm",{
	return ScriptingEngine.gitScriptRun(
            "https://github.com/CommonWealthRobotics/auto-configured-vitamins.git", // git location of the library
            "loadRobot.groovy" , // file to load
            null
            )
})
double indicator = 0
while((indicator =MobileBaseCadManager.get( base).getProcesIndictor().get())<1){
	println "Waiting for cad to get to 1:"+indicator
	ThreadUtil.wait(1000)
}

MobileBaseCadManager manager = MobileBaseCadManager.get(base)


manager.generateCad()
ThreadUtil.wait(1000)
indicator = 0
while((indicator =MobileBaseCadManager.get( base).getProcesIndictor().get())<1){
	println "Re-loading..."+indicator
	ThreadUtil.wait(1000)
}
