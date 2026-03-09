package com.rasugames.data

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

class GamePreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("burning_fruits_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_MAX_UNLOCKED_LEVEL = "max_unlocked_level"
        private const val KEY_LEADERBOARD = "leaderboard"
        private const val KEY_BEST_SCORE_PREFIX = "best_score_level_"
        private const val MAX_LEADERBOARD_ENTRIES = 10
    }

    var maxUnlockedLevel: Int
        get() = prefs.getInt(KEY_MAX_UNLOCKED_LEVEL, 1)
        set(value) = prefs.edit().putInt(KEY_MAX_UNLOCKED_LEVEL, value).apply()

    fun unlockNextLevel(currentLevel: Int) {
        if (currentLevel >= maxUnlockedLevel) {
            maxUnlockedLevel = (currentLevel + 1).coerceAtMost(21)
        }
    }

    fun getBestScore(level: Int): Int {
        return prefs.getInt("$KEY_BEST_SCORE_PREFIX$level", 0)
    }

    fun saveBestScore(level: Int, score: Int) {
        val current = getBestScore(level)
        if (score > current) {
            prefs.edit().putInt("$KEY_BEST_SCORE_PREFIX$level", score).apply()
        }
    }

    fun addLeaderboardEntry(level: Int, score: Int) {
        val entries = getLeaderboard().toMutableList()
        entries.add(LeaderboardEntry(level, score))
        entries.sortByDescending { it.score }
        val trimmed = entries.take(MAX_LEADERBOARD_ENTRIES)
        val jsonArray = JSONArray()
        trimmed.forEach { entry ->
            val obj = JSONObject()
            obj.put("level", entry.level)
            obj.put("score", entry.score)
            jsonArray.put(obj)
        }
        prefs.edit().putString(KEY_LEADERBOARD, jsonArray.toString()).apply()
    }

    fun getLeaderboard(): List<LeaderboardEntry> {
        val json = prefs.getString(KEY_LEADERBOARD, null) ?: return emptyList()
        val jsonArray = JSONArray(json)
        val entries = mutableListOf<LeaderboardEntry>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            entries.add(LeaderboardEntry(obj.getInt("level"), obj.getInt("score")))
        }
        return entries
    }
}

data class LeaderboardEntry(val level: Int, val score: Int)
