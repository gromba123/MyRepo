package com.example.myfootballcollectionkmp.data.dataSource

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.Upsert
import com.example.myfootballcollectionkmp.domain.model.football.Collectible
import com.example.myfootballcollectionkmp.domain.model.football.Game
import com.example.myfootballcollectionkmp.domain.model.user.User
import kotlinx.coroutines.flow.Flow

/**
Room database model. This won't be the final solution, just used for training and mock a real DB.
 Will be switched for a PostgresSQL database in the future. Couldn't use a pair of primary keys (userId, gameId)
 due to issues with SQLLite/Room compiler
 */

class StringListConverter {

    @TypeConverter
    fun toString(entity: List<String>): String {
        return entity.joinToString(",")
    }
    @TypeConverter
    fun fromString(serialized: String): List<String> {
        return serialized.split(",")
    }
}

@Dao
interface GamesCollectionDao {
    //<editor-fold desc="User">
    @Upsert
    suspend fun upsertUser(user: User)

    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUser(userId: String): User?

    @Query("DELETE FROM user WHERE id = :userId")
    suspend fun deleteUser(userId: String)
    //</editor-fold>

    //<editor-fold desc="Games">
    @Insert
    suspend fun insertGame(game: Game)

    @Query("SELECT gameId FROM user_games WHERE userId = :userId")
    fun getUserGames(userId: String): Flow<List<String>>

    @Query("DELETE FROM user_games WHERE userId = :userId")
    suspend fun deleteGame(userId: String)
    //</editor-fold>

    //<editor-fold desc="Collection">
    @Upsert
    suspend fun insertCollectionItem(collectible: Collectible)

    @Query("SELECT id, userId, team, season, description, bucketUrl, gameId FROM collectibles WHERE userId = :userId")
    fun getUserCollection(userId: String): Flow<List<Collectible>>

    @Query("DELETE FROM collectibles WHERE id = :id")
    suspend fun deleteCollectionItem(id: String)
    //</editor-fold>
}

@Database(entities = [User::class, Game::class, Collectible::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class MFCCollectionDatabase : RoomDatabase() {
    abstract fun getMFCCollectionDao(): GamesCollectionDao
}