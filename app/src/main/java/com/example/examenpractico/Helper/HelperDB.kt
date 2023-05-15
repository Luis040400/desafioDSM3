import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.examenpractico.Model.*


class HelperDB(context: Context?) : SQLiteOpenHelper(context,DB_NAME,null,DB_VERSION) {

    companion object{
        private const val DB_NAME = "examenpractico.sqlite"
        private const val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TipoAutomovil.CREATE_TABLE_CATEGORIA)
        db.execSQL(Colores.CREATE_TABLE_CATEGORIA)
        db.execSQL(Marcas.CREATE_TABLE_CATEGORIA)
        db.execSQL(Automovil.CREATE_TABLE_CATEGORIA)
        db.execSQL(Usuario.CREATE_TABLE_CATEGORIA)
        db.execSQL(FavoritosAutomovil.CREATE_TABLE_CATEGORIA)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

}