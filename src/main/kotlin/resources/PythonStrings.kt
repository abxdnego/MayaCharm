package resources

class PythonStrings {
    val UTF8_ENCODING_STR = "# -*- coding: utf-8 -*-"
    val OPEN_LOG = "import maya.cmds as cmds; cmds.cmdFileOutput(o=r\"{0}\")"
    val CLOSE_LOG = "import maya.cmds as cmds; cmds.cmdFileOutput(closeAll=True)"
    val EXECFILE = "python (\"execfile (\\\"{0}\\\")\");"

    val PYSTDERR = "# Error: "
    val PYSTDWRN = "# Warning: "

    val SETTRACE = "import pydevd; pydevd.settrace(host=\"{0}\", port={1,number,#}, suspend={2}, stdoutToServer={3}, stderrToServer={3})"
    val STOPTRACE = "import pydevd; pydevd.stoptrace()"

    private val _cmdportSetupScript: String

    val cmdportSetupScript: String
        get() = this._cmdportSetupScript

    init {
        _cmdportSetupScript = readStringFromResource("python/command_port_setup.py")
    }

    companion object {
        @JvmField
        val INSTANCE = PythonStrings()
        fun readStringFromResource(resource: String): String {
            return PythonStrings::class.java.classLoader.getResource(resource).readText()
        }
    }
}
