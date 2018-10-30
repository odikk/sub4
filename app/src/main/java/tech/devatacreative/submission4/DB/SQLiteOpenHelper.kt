package tech.devatacreative.submission4.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class SQLiteOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorites.db", null, 1){
    companion object {
        private var instance: ManagedSQLiteOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): SQLiteOpenHelper{
            if (instance == null){
                instance = SQLiteOpenHelper(ctx.applicationContext)
            }
            return instance as SQLiteOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Favorite.TABLE_FAVORITE, true,
                Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Favorite.MATCH_ID to TEXT + UNIQUE,
                Favorite.MATCH_DATE to TEXT,
                Favorite.TEAM_HOME_NAME to TEXT,
                Favorite.TEAM_AWAY_NAME to TEXT,
                Favorite.TEAM_HOME_SCORE to TEXT,
                Favorite.TEAM_AWAY_SCORE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Favorite.TABLE_FAVORITE, true)
    }
}

val Context.database: SQLiteOpenHelper
get() = SQLiteOpenHelper.getInstance(applicationContext)