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
        String createTableStatement1 = "CREATE TABLE " + Strings.PIECES + " (" + Strings.PIECE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Strings.PIECE_NAME + " TEXT, " + Strings.PIECE_COMPOSER + " TEXT, " + Strings.PIECE_PERIOD + " TEXT, " + Strings.PIECE_KEY + " TEXT, " + Strings.PIECE_OPUS + " TEXT, " + Strings.YOUTUBE_LINK + " TEXT, " + Strings.PIECE_DESCRIPTION + " TEXT, " + Strings.PIECE_DYNAMICS + " TEXT, " + Strings.PIECE_TYPE + " TEXT);";
        String createTableStatement2 = "CREATE TABLE " + Strings.SCORES + " (" + Strings.SCORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Strings.SCORES_TYPE + " TEXT, " + Strings.SCORES_SUBTYPE + " TEXT, " + Strings.SCORES_PIECE_ID + " INTEGER, " + Strings.SCORE_1 + " TEXT, " + Strings.SCORE_2 + " TEXT, " + Strings.SCORE_3 + " TEXT);";
        String createTableStatement3 = "CREATE TABLE " + Strings.PATHS + " (" + Strings.PATH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Strings.PATH_TYPE + " TEXT, " + Strings.PATH_SUBTYPE + " TEXT);";

        db.execSQL(createTableStatement1);
        db.execSQL(createTableStatement2);
        db.execSQL(createTableStatement3);
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

        //Piece key - from -5 to 5; -5 meaning piece is maximally minor, 5 means piece is maximally major
        //Dynamics - from -5 to 5 where -5 is the least and 5 is the most dynamic piece
        //Piece type - any number greater than 0; it is important to be consistent and use same number for one piece type


        String samplevalues = "INSERT INTO \"main\".\"PIECES\" (\"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ('Sonata 11 - Grand Pathetique', 'Ludwig van Beethoven', 'Classicism',  '-3Cminor', '1Sonata', '13', 'YlKxaIo_mgc', '4', 'Ludwig van Beethoven s Piano Sonata No. 8 in C minor, Op. 13, commonly known as Sonata Pathétique, was written in 1798 when the composer was 27 years old, and was published in 1799. It has remained one of his most celebrated compositions. Beethoven dedicated the work to his friend Prince Karl von Lichnowsky. Although commonly thought to be one of the few works to be named by the composer himself, it was actually named Grande sonate pathétique (to Beethoven s liking) by the publisher, who was impressed by the sonata s tragic sonorities');\n";
        String samplevalues2 = "INSERT INTO \"main\".\"PIECES\" (\"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ('Sonata 17 - Tempest', 'Ludwig van Beethoven', 'Classicism',  '-2Dminor', '1Sonata', '31', 'auoSk1ExjCo','4', 'The Piano Sonata No. 17 in D minor, Op. 31, No. 2, was composed in 1801–02 by Ludwig van Beethoven. It is usually referred to as The Tempest (or Der Sturm in his native German), but the sonata was not given this title by Beethoven, or indeed referred to as such during his lifetime. The name comes from a reference to a personal conversation with Beethoven by his associate Anton Schindler in which Schindler reports that Beethoven suggested, in passing response to his question about interpreting it and Op. 57, the Appassionata sonata, that he should read Shakespeare s Tempest; some however have suggested that Beethoven may have been referring to the works of C. C. Sturm, the preacher and author best known for his Reflections on the Works of God in Nature, a copy of which he owned and, indeed, had heavily annotated. Although much of Schindler s information is distrusted by classical music scholars, this is a first-hand account unlike any other that any scholar reports.' );\n";
        String samplevalues3 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Sonata 32', 'Ludwig van Beethoven', 'Classicism',  '-4Cminor', '1Sonata', '111', 'WGg9cE-ceso','4', 'The Piano Sonata No. 32 in C minor, Op. 111, is the last of Ludwig van Beethoven s piano sonatas. The work was written between 1821 and 1822. Like other late period sonatas, it contains fugal elements. It was dedicated to his friend, pupil, and patron, Archduke Rudolf. The sonata consists of only two contrasting movements. The second movement is marked as an arietta with variations. Thomas Mann called it \"farewell to the sonata form\". The work entered the repertoire of leading pianists only in the second half of the 19th century. Rhythmically visionary and technically demanding, it is one of the most discussed of Beethovens works');\n";
        String samplevalues4 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\",\"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Sonata 21 - Waldstein', 'Ludwig van Beethoven', 'Classicism',  '5 Cmajor', '1Sonata', '53 ', 'elJUO93uYzE', '5', 'Beethoven s Piano Sonata No. 21 in C major, Op. 53, known as the Waldstein, is one of the three most notable sonatas of his middle period (the other two being the Appassionata, Op. 57, and Les Adieux, Op. 81a). Completed in summer 1804 and surpassing Beethoven s previous piano sonatas in its scope, the Waldstein is a key early work of Beethoven s Heroic decade (1803–1812) and set a standard for piano composition in the grand manner. The sonata s name derives from Beethoven s dedication to his close friend and patron Count Ferdinand Ernst Gabriel von Waldstein of Vienna. Like the Archduke Trio (one of many pieces dedicated to Archduke Rudolph), it is named for Waldstein even though other works are dedicated to him. It is also known as LAurora (The Dawn) in Italian, for the sonority of the opening chords of the third movement, thought to conjure an image of daybreak.');\n";
        String samplevalues5 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Sonata 14 - Moonlight', 'Ludwig van Beethoven', 'Classicism',  '-5Csharpminor', '1Sonata', '27 ', 'Ua_GHVEDCdI', '4', 'The Piano Sonata No. 14 in C-sharp minor, marked Quasi una fantasia, Op. 27, No. 2, is a piano sonata by Ludwig van Beethoven. It was completed in 1801 and dedicated in 1802 to his pupil, Countess Giulietta Guicciardi. The popular name Moonlight Sonata goes back to a critic s remark after Beethoven s death. The piece is one of Beethoven s most popular compositions for the piano, and it was a popular favorite even in his own day. Beethoven wrote the Moonlight Sonata in his early thirties, after he had finished with some commissioned work; there is no evidence that he was commissioned to write this sonata. ');\n";
        String samplevalues6 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Sonata 30', 'Ludwig van Beethoven', 'Classicism',  '0 Emajor', '1Sonata', '109 ', 'KbvuOiiCBPQ','1', 'Ludwig van Beethoven s Piano Sonata No. 30 in E major, Op.109, composed in 1820, is the antepenultimate of his piano sonatas. In it, after the huge Hammerklavier Sonata, Op. 106, Beethoven returns to a smaller scale and a more intimate character. ');\n";
        String samplevalues7 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Sonata 29 - Hammerklavier', 'Ludwig van Beethoven', 'Classicism',  '2 Bflatmajor', '1Sonata', '106 ', 'ETjfAIHpJjY','1', 'Ludwig van Beethoven s Piano Sonata No. 29 in B♭ major, Op. 106 (known as the Große Sonate für das Hammerklavier, or more simply as the Hammerklavier) is a piano sonata that is widely viewed as one of the most important works of the composer s third period and among the greatest piano sonatas of all time. Completed in 1818, it is often considered to be Beethoven s most technically challenging piano composition[1] and one of the most demanding solo works in the classical piano repertoire. The first documented public performance was in 1836 by Franz Liszt in the Salle Erard in Paris.');\n";
        String samplevalues8 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Sonata 12 - Funeral march', 'Ludwig van Beethoven', 'Classicism',  '2 Aflatmajor', '1Sonata', '26 ', 'O1WnuPgpJg0','1', 'Ludwig van Beethoven composed his Piano Sonata No. 12 in A♭ major, Op. 26, in 1800–1801, around the same time as he completed his First Symphony. He dedicated the sonata to Prince Karl von Lichnowsky, who had been his patron since 1792. Consisting of four movements, the sonata takes around 20–22 minutes to perform. ');\n";
        String samplevalues9 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Sonata 31', 'Ludwig van Beethoven', 'Classicism',  '-1Aflatmajor', '1Sonata', '110 ', 'hhpI6vpOLVo','3', 'The Piano Sonata No. 31 in A♭ major, Op. 110, by Ludwig van Beethoven was composed in 1821. It is the central piano sonata in the group of three, Opp. 109–111, which he wrote between 1820 and 1822, and the thirty-first of his published piano sonatas. The work is in three movements. The moderato first movement in sonata form, marked con amabilità, is followed by a fast scherzo. The finale comprises a slow recitative and arioso dolente, a fugue, a return of the arioso lament, and a second fugue that builds to an affirmative conclusion.');\n";
        String samplevalues10 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Symphony 2', 'Ludwig van Beethoven', 'Classicism',  '2 Dmajor', '2Symphony', '36 ', 'bEiYmeeV6sI','3', 'The Symphony No. 2 in D major, Op. 36, is a symphony in four movements written by Ludwig van Beethoven between 1801 and 1802. The work is dedicated to Karl Alois, Prince Lichnowsky. ');\n";
        String samplevalues11 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Symphony 9 Ode to Joy', 'Ludwig van Beethoven', 'Classicism',  '-4Dminor', '2Symphony', '125 ', 'rOjHhS5MtvA','0', 'The Symphony No. 9 in D minor, Op. 125, is a choral symphony, the final complete symphony by Ludwig van Beethoven, composed between 1822 and 1824. It was first performed in Vienna on 7 May 1824. The symphony is regarded by many critics and musicologists as Beethoven s greatest work and one of the supreme achievements in the history of music. One of the best-known works in common practice music, it stands as one of the most performed symphonies in the world');\n";
        String samplevalues12 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Symphony 5', 'Ludwig van Beethoven', 'Classicism',  '-2Cminor', '2Symphony', '67 ', 'fOk8Tm815lE','5', 'The Symphony No. 5 in C minor of Ludwig van Beethoven, Op. 67, was written between 1804 and 1808. It is one of the best-known compositions in classical music and one of the most frequently played symphonies, and it is widely considered one of the cornerstones of western music. First performed in Vienna s Theater an der Wien in 1808, the work achieved its prodigious reputation soon afterward. E. T. A. Hoffmann described the symphony as one of the most important works of the time. ');\n";
        String samplevalues13 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Symphony 7', 'Ludwig van Beethoven', 'Classicism',  '2 Amajor', '2Symphony', '92 ', '-4788Tmz9Zo','2', 'The Symphony No. 7 in A major, Op. 92, is a symphony in four movements composed by Ludwig van Beethoven between 1811 and 1812, while improving his health in the Bohemian spa town of Teplice. The work is dedicated to Count Moritz von Fries.At its premiere, Beethoven was noted as remarking that it was one of his best works. The second movement, Allegretto, was the most popular movement and had to be encored. The instant popularity of the Allegretto resulted in its frequent performance separate from the complete symphony.');\n";

        String samplevalues14 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Toccata and Fugue', 'J.S. Bach', 'Baroque',  '-3 Dminor', '3Prelude and Fugue', '565', 'aJQOXTI9BJI','0', 'The Toccata and Fugue in D minor, BWV 565, is a piece of organ music written, according to its oldest extant sources, by Johann Sebastian Bach (1685–1750). The piece opens with a toccata section, followed by a fugue that ends in a coda.');\n";
        String samplevalues15 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'WTC I Prelude and Fugue 2', 'J.S. Bach', 'Baroque',  '-4Cminor', '3Prelude and Fugue', '847' , 'I1MFOgx1NYY', '1', 'The second prelude and fugue set from First The Well Tempered Clavier Book of J.S. Bach. ');\n";
        String samplevalues16 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'WTC I Prelude and Fugue 8', 'J.S. Bach', 'Baroque',  '-4Eflatminor', '3Prelude and Fugue', '853', 'gXnRl6VHzjI','-3', 'The eight prelude and fugue set from First The Well Tempered Clavier Book of J.S. Bach.');\n";
        String samplevalues17 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'WTC I Prelude and Fugue 1', 'J.S. Bach', 'Baroque',  '3 Cmajor', '3Prelude and Fugue', '846', '0KQW2YnCUrE','-3', 'The first prelude and fugue set from First The Well Tempered Clavier Book of J.S. Bach.');\n";
        String samplevalues18 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'WTC I Prelude and Fugue 4', 'J.S. Bach', 'Baroque',  '-3Csharpminor', '3Prelude and Fugue', '849', 'f-ezSE-TmP0','-3', 'The fourth prelude and fugue set from First The Well Tempered Clavier Book of J.S. Bach.');\n";
        String samplevalues19 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Goldberg Variations', 'J.S. Bach', 'Baroque',  '1 Gmajor', '3Prelude and Fugue', '998', 'Ah392lnFHxM','2', 'The Goldberg Variations, BWV 988, is a musical composition for harpsichord by Johann Sebastian Bach, consisting of an aria and a set of 30 variations. First published in 1741, it is named after Johann Gottlieb Goldberg, who may also have been the first performer of the work. [For this work] we have to thank the instigation of the former Russian ambassador to the electoral court of Saxony, Count Kaiserling, who often stopped in Leipzig and brought there with him the aforementioned Goldberg, in order to have him given musical instruction by Bach. The Count was often ill and had sleepless nights. At such times, Goldberg, who lived in his house, had to spend the night in an antechamber, so as to play for him during his insomnia. ... Once the Count mentioned in Bach s presence that he would like to have some clavier pieces for Goldberg, which should be of such a smooth and somewhat lively character that he might be a little cheered up by them in his sleepless nights. Bach thought himself best able to fulfill this wish by means of Variations, the writing of which he had until then considered an ungrateful task on account of the repeatedly similar harmonic foundation. But since at this time all his works were already models of art, such also these variations became under his hand. Yet he produced only a single work of this kind. Thereafter the Count always called them his variations. He never tired of them, and for a long time sleepless nights meant: Dear Goldberg, do play me one of my variations. Bach was perhaps never so rewarded for one of his works as for this. The Count presented him with a golden goblet filled with 100 louis-d or. Nevertheless, even had the gift been a thousand times larger, their artistic value would not yet have been paid for.');\n";
        String samplevalues20 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Air on the G String', 'J.S. Bach', 'Baroque',  '4 Dmajor', '4Orchestral Suite', '998', 'pzlw6fUux4o','0', ' Air on the G String, also known as Air for G String and Celebrated Air, is August Wilhelmj s 1871 arrangement of the second movement of Johann Sebastian Bach s Orchestral Suite No. 3 in D major, BWV 1068. The arrangement differs from the original in that the part of the first violins is transposed down so that it can be played entirely on a violin s lowest string, i.e., the G string. It is played by a single violin (instead of by the first violins as a group). ');\n";

        String samplevalues21 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Ballade No. 1', 'Fryderyk Chopin', 'Romanticism',  '-3 Gminor', '5Ballade', '23', 'Ce8p0VcTbuA','4', 'First of Chopins four ballades. The term ballade was used by Chopin in the sense of a balletic interlude or dance-piece, equivalent to the old Italian ballata, but the term may also have connotations of the medieval heroic ballad, a narrative minstrel-song, often of a fantastical character. There are dramatic and dance-like elements in Chopin s use of the genre, and he may be said to be a pioneer of the ballade as an abstract musical form. The four ballades are said to have been inspired by poet Adam Mickiewicz. The exact inspiration for each individual ballade, however, is unclear and disputed. ');\n";
        String samplevalues22 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Chopin Nocturne No. 20', 'Fryderyk Chopin', 'Romanticism',  '-3 Csharpminor', '6Nocturne', 'post op', 'n9oQEa-d5rU','0', 'The Nocturne No. 20 in C-sharp minor, Op. posth., Lento con gran espressione, P 1, No. 16, KKIVa/16, is a solo piano piece composed by Frédéric Chopin in 1830 and published in 1870. Chopin dedicated this work to his older sister Ludwika Chopin, with the statement:To my sister Ludwika as an exercise before beginning the study of my second Concerto First published 21 years after the composer s death, the piece is usually referred to as Lento con gran espressione, from its tempo marking. It is sometimes also called Reminiscence. The piece was played by Holocaust survivor Natalia Karp for the Nazi concentration camp commandant Amon Goeth, with Goeth being so impressed with the rendition that he spared Karp s life.  ');\n";
        String samplevalues23 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Prelude in D minor 24', 'Fryderyk Chopin', 'Romanticism',  '-3 Dminor', '7Prelude', '28', 'rqPr1A29Meo','4', 'The twenty-fourth prelude from Chopin s series od preludes released under opus 24. ');\n";
        String samplevalues24 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Prelude in E minor 4', 'Fryderyk Chopin', 'Romanticism',  '-5 Eminor', '7Prelude', '28', 'KHGHhYZCIQI','0', 'The fourth prelude from Chopin s series od preludes released under opus 24. ');\n";
        String samplevalues25 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Chopin Etude 12 Ocean', 'Fryderyk Chopin', 'Romanticism',  '-5 Cminor', '8Etude', '25', 'xlgOju2TY28','4', 'The last etude from the second set of Etudes from Federic Chopin. ');\n";
        String samplevalues26 = "INSERT INTO \"main\".\"PIECES\" ( \"PIECE_NAME\", \"PIECE_COMPOSER\", \"PIECE_PERIOD\", \"PIECE_KEY\", \"PIECE_TYPE\", \"PIECE_OPUS\", \"YOUTUBE_LINK\", \"PIECE_DYNAMICS\", \"PIECE_DESCRIPTION\") VALUES ( 'Chopin Etude 12 Revolutionary', 'Fryderyk Chopin', 'Romanticism',  '-5 Cminor', '8Etude', '25', 'seC8oc_1rYQ','4', 'The last etude from the first set of Etudes from Federic Chopin. Probably the most widely known Chopins piece');\n";


        db.execSQL(samplevalues);
        db.execSQL(samplevalues2);
        db.execSQL(samplevalues3);
        db.execSQL(samplevalues4);
        db.execSQL(samplevalues5);
        db.execSQL(samplevalues6);
        db.execSQL(samplevalues7);
        db.execSQL(samplevalues8);
        db.execSQL(samplevalues9);
        db.execSQL(samplevalues10);
        db.execSQL(samplevalues11);
        db.execSQL(samplevalues12);
        db.execSQL(samplevalues13);

        db.execSQL(samplevalues14);
        db.execSQL(samplevalues15);
        db.execSQL(samplevalues16);
        db.execSQL(samplevalues17);
        db.execSQL(samplevalues18);
        db.execSQL(samplevalues19);
        db.execSQL(samplevalues20);

        db.execSQL(samplevalues21);
        db.execSQL(samplevalues22);
        db.execSQL(samplevalues23);
        db.execSQL(samplevalues24);
        db.execSQL(samplevalues25);
        db.execSQL(samplevalues26);


    return true;
    }
}
