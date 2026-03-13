package com.rasugames.utils.core

import android.content.Context
import com.rasugames.utils.network.SimInfo
import com.rasugames.utils.network.VpnInfo
import com.rasugames.utils.network.ProxyInfo
import com.rasugames.utils.network.WifiInfo
import com.rasugames.utils.network.TimezoneInfo
import com.rasugames.utils.sensor.GyroscopeInfo
import com.rasugames.utils.sensor.AccelerometerInfo
import com.rasugames.utils.sensor.LightSensorInfo
import com.rasugames.utils.sensor.AudioVolumeInfo
import com.rasugames.utils.sensor.MagnetometerInfo
import com.rasugames.utils.sensor.ProximitySensorInfo
import com.rasugames.utils.system.WidevineInfo
import com.rasugames.utils.system.CpuInfo
import com.rasugames.utils.system.BuildInfo
import com.rasugames.utils.system.BatteryInfo
import com.rasugames.utils.system.UptimeInfo
import com.rasugames.utils.system.BrightnessInfo
import com.rasugames.utils.system.InstallSourceInfo
import com.rasugames.utils.system.AccessibilityInfo
import com.rasugames.utils.system.RootInfo

class DevicePropertiesResult private constructor(
    private val sim: String,
    private val vpn: String,
    private val proxy: String,
    private val wifi: String,
    private val timezone: String,
    private val root: String,

    private val gyroscope: String,
    private val accelerometer: String,
    private val lightSensor: String,
    private val audioVolume: String,
    private val magnetometer: String,
    private val proximity: String,

    private val widevineId: String,

    private val cpuHash: String,

    private val buildInfo: String,

    private val charging: String,
    private val uptime: String,
    private val brightness: String,
    

    private val installSource: String,
    private val accessibility: String
) {
    
    companion object {
        suspend fun create(context: Context): DevicePropertiesResult {
            val motionResult = Vasdf.getResult(context)
            return DevicePropertiesResult(
                // x4 - Network & Security
                sim = SimInfo().collect(context),
                vpn = VpnInfo().collect(context),
                proxy = ProxyInfo().collect(context),
                wifi = WifiInfo().collect(context),
                timezone = TimezoneInfo().collect(),
                root =  RootInfo().collect(context),
                
                // x5 - Sensors
                gyroscope = GyroscopeInfo().collect(motionResult),
                accelerometer = AccelerometerInfo().collect(motionResult),
                lightSensor = LightSensorInfo().collect(motionResult),
                audioVolume = AudioVolumeInfo().collect(context),
                magnetometer = MagnetometerInfo().collect(motionResult),
                proximity = ProximitySensorInfo().collect(motionResult),
                
                // x8 - Device ID
                widevineId = WidevineInfo().collect(context),
                
                // x9 - CPU
                cpuHash = CpuInfo().collect(context),
                
                // x10 - BUILD
                buildInfo = BuildInfo().collect(context),
                
                // s28 - CHRG, UP, BRIGHT
                charging = BatteryInfo().collect(context),
                uptime = UptimeInfo().collect(context),
                brightness = BrightnessInfo().collect(context),
                
                // s30 - INSTALL, A11Y
                installSource = InstallSourceInfo().collect(context),
                accessibility = AccessibilityInfo().collect(context)
            )
        }
    }
    
    /**
     * x4 - Network & Security
     * @return "SIM[value];VPN[value];PROXY[value];WIFI[value];TZ[value];ROOT[value]"
     */
    fun getX4(): String = listOf(sim, vpn, proxy, wifi, timezone, root)
        .filter { it.isNotBlank() }
        .joinToString(";")
    
    /**
     * x5 - Sensors
     * @return "GYRO[value];ACC[value];LIGHT[value];MAGN[value];PROXIMITY[value]"
     */
    fun getX5(): String = listOf(gyroscope, accelerometer, lightSensor, magnetometer, proximity)
        .filter { it.isNotBlank() }
        .joinToString(";")
    
    /**
     * x7 - Reserved
     */
    fun getX7(): String = ""
    
    /**
     * x8 - Device ID
     * @return "WID[value]"
     */
    fun getX8(): String = widevineId
    
    /**
     * x9 - CPU
     * @return "CPU[value]"
     */
    fun getX9(): String = cpuHash
    
    /**
     * x10 - BUILD
     * @return "BUILD[value]"
     */
    fun getX10(): String = buildInfo
    
    /**
     * s28 - CHRG, UP, BRIGHT
     * @return "CHRG[value];UP[value];BRIGHT[value];VOL[value]"
     */
    fun getS28(): String = listOf(charging, uptime, brightness, audioVolume)
        .filter { it.isNotBlank() }
        .joinToString(";")
    
    /**
     * s30 - INSTALL, A11Y
     * @return "INSTALL[value];A11Y[value]"
     */
    fun getS30(): String = listOf(installSource, accessibility)
        .filter { it.isNotBlank() }
        .joinToString(";")
}
