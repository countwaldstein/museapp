package com.kawo.museapp;

import android.content.ContentValues;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;


public class DataBaseHelper extends SQLiteOpenHelper {

    public class Strings {

        public static final String PIECES = "PIECES";
        public static final String PIECE_NAME = "PIECE_NAME";
        public static final String PIECE_COMPOSER = "PIECE_COMPOSER";
        public static final String PIECE_PERIOD = "PIECE_PERIOD";
        public static final String PIECE_KEY = "PIECE_KEY";
        public static final String PIECE_TYPE = "PIECE_TYPE";
        public static final String PIECE_OPUS = "PIECE_OPUS";
        public static final String PIECE_ID = "PIECE_ID";
        public static final String YOUTUBE_LINK = "YOUTUBE_LINK";
        public static final String PIECE_DESCRIPTION = "PIECE_DESCRIPTION";
        public static final String PIECE_DYNAMICS = "PIECE_DYNAMICS";

        public static final String PATHS = "PATHS";
        public static final String PATH_TYPE = "PATH_TYPE";
        public static final String PATH_SUBTYPE = "PATH_SUBTYPE";
        public static final String PATH_ID = "PATH_ID";

        public static final String SCORES = "SCORES";
        public static final String SCORES_TYPE = "SCORES_TYPE";
        public static final String SCORES_SUBTYPE = "SCORES_SUBTYPE";
        public static final String SCORE_1 = "SCORE_1";
        public static final String SCORE_2 = "SCORE_2";
        public static final String SCORE_3 = "SCORE_3";
        public static final String SCORES_PIECE_ID = "SCORES_PIECE_ID";
        public static final String SCORE_ID = "SCORE_ID";
    }

    public DataBaseHelper(@Nullable Context context) {
        super(context, "museapp_database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement2 = "CREATE TABLE " + Strings.PIECES + " (" + Strings.PIECE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Strings.PIECE_NAME + " TEXT, " + Strings.PIECE_COMPOSER + " TEXT, " + Strings.PIECE_PERIOD + " TEXT, " + Strings.PIECE_KEY + " TEXT, " + Strings.PIECE_OPUS + " TEXT, " + Strings.YOUTUBE_LINK + " TEXT, " + Strings.PIECE_DESCRIPTION + " TEXT, " + Strings.PIECE_DYNAMICS + " TEXT, " + Strings.PIECE_TYPE + " TEXT);";
        String createTableStatement4 = "CREATE TABLE " + Strings.SCORES + " (" + Strings.SCORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Strings.SCORES_TYPE + " TEXT, " + Strings.SCORES_SUBTYPE + " TEXT, " + Strings.SCORES_PIECE_ID + " INTEGER, " + Strings.SCORE_1 + " TEXT, " + Strings.SCORE_2 + " TEXT, " + Strings.SCORE_3 + " TEXT);";
        String createTableStatement5 = "CREATE TABLE " + Strings.PATHS + " (" + Strings.PATH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Strings.PATH_TYPE + " TEXT, " + Strings.PATH_SUBTYPE + " TEXT);";
        db.execSQL(createTableStatement2);

        db.execSQL(createTableStatement4);
        db.execSQL(createTableStatement5);
        insertValues(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE PIECES");
        String createTableStatement2 = "CREATE TABLE " + Strings.PIECES + " (" + Strings.PIECE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Strings.PIECE_NAME + " TEXT, " + Strings.PIECE_COMPOSER + " TEXT, " + Strings.PIECE_PERIOD + " TEXT, " + Strings.PIECE_KEY + " TEXT, " + Strings.PIECE_OPUS + " TEXT, " + Strings.YOUTUBE_LINK + " TEXT, " + Strings.PIECE_DESCRIPTION + " TEXT, " + Strings.PIECE_DYNAMICS + " TEXT, " + Strings.PIECE_TYPE + " TEXT);";
        db.execSQL(createTableStatement2);

        insertValues(db);
    }

    public boolean insertPath(String type, String subtype) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Strings.PATH_TYPE, type);
        cv.put(Strings.PATH_SUBTYPE, subtype);

        long insert = db.insert(Strings.PATHS, null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertValues(SQLiteDatabase db) {

        String samplevalues = "INSERT INTO \"main\".\"PIECES\" (\"PIECE_ID\", \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DESCRIPTION\", \"PIECE_DYNAMICS\") VALUES ('1', 'Grand Sonata Pathetique', 'Beethoven', 'Classicism',  '-3Cminor', '1Sonata', '13', 'YlKxaIo_mgc', 'Ludwig van Beethoven s Piano Sonata No. 8 in C minor, Op. 13, commonly known as Sonata Pathétique, was written in 1798 when the composer was 27 years old, and was published in 1799. It has remained one of his most celebrated compositions. Beethoven dedicated the work to his friend Prince Karl von Lichnowsky. Although commonly thought to be one of the few works to be named by the composer himself, it was actually named Grande sonate pathétique (to Beethoven s liking) by the publisher, who was impressed by the sonata s tragic sonorities','4');\n";
//        String samplevalues2 = "INSERT INTO \"main\".\"PIECES\" (\"PIECE_ID\", \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\") VALUES ('2', 'Winter', 'Vivaldi', '', '', '');\n";
        String samplevalues3 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DESCRIPTION\", \"PIECE_DYNAMICS\") VALUES ( 'Sonata 32', 'Beethoven', 'Classicism',  '-4Cminor', '1Sonata', '111', 'WGg9cE-ceso', 'The Piano Sonata No. 32 in C minor, Op. 111, is the last of Ludwig van Beethoven s piano sonatas. The work was written between 1821 and 1822. Like other late period sonatas, it contains fugal elements. It was dedicated to his friend, pupil, and patron, Archduke Rudolf. The sonata consists of only two contrasting movements. The second movement is marked as an arietta with variations. Thomas Mann called it \"farewell to the sonata form\". The work entered the repertoire of leading pianists only in the second half of the 19th century. Rhythmically visionary and technically demanding, it is one of the most discussed of Beethovens works','4');\n";
        String samplevalues4 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DESCRIPTION\", \"PIECE_DYNAMICS\") VALUES ( 'Waldstein Sonata', 'Beethoven', 'Classicism',  '5 Cmajor', '1Sonata', '53 ', 'elJUO93uYzE', 'Beethoven s Piano Sonata No. 21 in C major, Op. 53, known as the Waldstein, is one of the three most notable sonatas of his middle period (the other two being the Appassionata, Op. 57, and Les Adieux, Op. 81a). Completed in summer 1804 and surpassing Beethoven s previous piano sonatas in its scope, the Waldstein is a key early work of Beethoven s Heroic decade (1803–1812) and set a standard for piano composition in the grand manner. The sonata s name derives from Beethoven s dedication to his close friend and patron Count Ferdinand Ernst Gabriel von Waldstein of Vienna. Like the Archduke Trio (one of many pieces dedicated to Archduke Rudolph), it is named for Waldstein even though other works are dedicated to him. It is also known as LAurora (The Dawn) in Italian, for the sonority of the opening chords of the third movement, thought to conjure an image of daybreak. It is considered one of Beethoven s greatest and most technically challenging piano sonatas. The first section of the rondo requires a simultaneous pedal trill, high melody and rapid left hand runs while its coda s glissando octaves, written in dialogue between the hands, compel even advanced performers to play in a simplified version since it is more demanding to play on the heavier action of a modern piano than on an early 19th-century instrument. An average performance of the entire Waldstein lasts about twenty-five minutes. ','5');\n";
        String samplevalues5 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DESCRIPTION\", \"PIECE_DYNAMICS\") VALUES ( 'Moonlight Sonata', 'Beethoven', 'Classicism',  '-5C#minor', '1Sonata', '53 ', 'Ua_GHVEDCdI', 'The Piano Sonata No. 14 in C-sharp minor, marked Quasi una fantasia, Op. 27, No. 2, is a piano sonata by Ludwig van Beethoven. It was completed in 1801 and dedicated in 1802 to his pupil, Countess Giulietta Guicciardi.[b] The popular name Moonlight Sonata goes back to a critic s remark after Beethoven s death. The piece is one of Beethoven s most popular compositions for the piano, and it was a popular favorite even in his own day.[1] Beethoven wrote the Moonlight Sonata in his early thirties, after he had finished with some commissioned work; there is no evidence that he was commissioned to write this sonata. ','4');\n";




        db.execSQL(samplevalues);
//        db.execSQL(samplevalues2);
        db.execSQL(samplevalues3);
        db.execSQL(samplevalues4);
        db.execSQL(samplevalues5);


    return true;
    }
}
