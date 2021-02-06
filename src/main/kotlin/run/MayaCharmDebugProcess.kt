package run

import MayaBundle as Loc
import mayacomms.MayaCommandInterface
import settings.ApplicationSettings
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.execution.ui.ExecutionConsole
import com.intellij.xdebugger.XDebugSession
import com.jetbrains.python.debugger.PyDebugProcess
import java.net.ServerSocket

class MayaCharmDebugProcess(session: XDebugSession,
                            serverSocket: ServerSocket,
                            executionConsole: ExecutionConsole,
                            processHandler: ProcessHandler?,
                            private val runConfig: MayaCharmRunConfiguration?,
                            private val pid: Int)
                            : PyDebugProcess(session, serverSocket, executionConsole, processHandler, false) {

    override fun getConnectionMessage(): String {
        return Loc.message("mayacharm.debugproc.ConnectionMessage", pid.toString())
    }

    override fun getConnectionTitle(): String {
        return Loc.message("mayacharm.debugproc.ConnectionTitle")
    }

    override fun afterConnect() {
        runConfig ?: return

        val sdkSettings = ApplicationSettings.INSTANCE.mayaSdkMapping[runConfig.mayaSdkPath]
        val maya = MayaCommandInterface(sdkSettings!!.port)

        if (isConnected) {
            when (runConfig.executionType) {
                ExecutionType.FILE -> maya.sendFileToMaya(runConfig.scriptFilePath)
                ExecutionType.CODE -> maya.sendCodeToMaya(runConfig.scriptCodeText)
            }
        }
        else {
            printToConsole(Loc.message("mayacharm.debugproc.FailedToConnect"), ConsoleViewContentType.SYSTEM_OUTPUT)
        }
    }
}
