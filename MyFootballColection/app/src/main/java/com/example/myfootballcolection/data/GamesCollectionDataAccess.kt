package com.example.myfootballcolection.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.Upsert
import com.example.myfootballcolection.domain.CollectionItem
import kotlinx.coroutines.flow.Flow

/**
Room database model. This won't be the final solution, just used for training and mock a real DB.
 Will be switched for a PostgresSQL database in the future. Couldn't use a pair of primary keys (userId, gameId)
 due to issues with SQLLite/Room compiler
 */

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val password: String,
    val tag: String,
    val profilePictureUrl: String,
    val followingTeams: List<String>
)

@Entity(
    tableName = "user_games",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId", "gameId"], unique = true)]
)
data class GameEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val gameId: String
)

@Entity(
    tableName = "user_collection",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class CollectionItemEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val team: String,
    val season: String,
    val description: String,
    val bucketUrl: String,
    val gameId: String?
)

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
interface GamesCollectionDataAccess {
    //<editor-fold desc="User">
    @Upsert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUser(userId: String): UserEntity

    @Query("DELETE FROM user WHERE id = :userId")
    suspend fun deleteUser(userId: String)
    //</editor-fold>

    //<editor-fold desc="Games">
    @Insert
    suspend fun insertGame(game: GameEntity)

    @Query("SELECT gameId FROM user_games WHERE userId = :userId")
    fun getUserGames(userId: String): Flow<List<String>>

    @Query("DELETE FROM user_games WHERE userId = :userId")
    suspend fun deleteGame(userId: String)
    //</editor-fold>

    //<editor-fold desc="Collection">
    @Upsert
    suspend fun insertCollectionItem(collection: CollectionItemEntity)

    @Query("SELECT id, team, season, description, bucketUrl, gameId FROM user_collection WHERE userId = :userId")
    fun getUserCollection(userId: String): Flow<List<CollectionItem>>

    @Query("DELETE FROM user_collection WHERE id = :id")
    suspend fun deleteCollectionItem(id: String)
    //</editor-fold>
}

@Database(entities = [UserEntity::class, GameEntity::class, CollectionItemEntity::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class MFCCollectionDatabase : RoomDatabase() {
    abstract fun getMFCCollectionDao(): GamesCollectionDataAccess
}