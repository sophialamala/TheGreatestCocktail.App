package fr.isen.sophie.thegreatestcocktailapp.fr.isen.sophie.thegreatestcocktailapp

import android.content.Context


object FavoriteManager {
    private const val PREFS_NAME = "favorites_prefs"
    private const val KEY_IDS = "favorite_ids"

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getIds(context: Context): List<String> {
        val raw = prefs(context).getString(KEY_IDS, "") ?: ""
        if (raw.isBlank()) return emptyList()
        return raw.split(",").map { it.trim() }.filter { it.isNotBlank() }
    }

    fun isFavorite(context: Context, id: String): Boolean =
        getIds(context).contains(id)

    fun toggle(context: Context, id: String) {
        val list = getIds(context).toMutableList()
        if (list.contains(id)) list.remove(id) else list.add(0, id)
        prefs(context).edit().putString(KEY_IDS, list.joinToString(",")).apply()
    }
}