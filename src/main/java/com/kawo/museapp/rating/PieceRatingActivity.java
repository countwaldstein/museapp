package com.kawo.museapp.rating;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.kawo.museapp.DataBaseHelper;
import com.kawo.museapp.R;
import com.kawo.museapp.SplashScreen;
import com.kawo.museapp.YouTubeConfig;
import com.kawo.museapp.dashboard.DashBoardCardType;
import com.kawo.museapp.dashboard.MainDashBoard;


import android.text.BoringLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Float.parseFloat;
import static java.lang.Float.valueOf;
import static java.lang.Integer.parseInt;


public class PieceRatingActivity extends YouTubeBaseActivity {

    public YouTubePlayer youTubePlayer;
    public boolean isYouTubePlayerFullScreen;
    YouTubePlayerView mYouTubePlayerView;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    private static final String TAG = "Piece Rating Activity";
    TextView ttitle, tcomposer, tperiod, ttype, tdesc;
    RatingBar rbar1, rbar2, rbar3;
    Button buttrat1;
    RatingObject mRating;
    TextView rtext1, rtext2, rtext3;
    boolean fullScreen;
    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDataBase;
    private PieceObject mPieceObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piece_rating2);

        Bundle bundle = getIntent().getExtras();
        String pathtype = bundle.getString("Type");
        String pathsubtype = bundle.getString("Subtype");


        Log.i("path1type1", pathtype);
        Log.i("path1subtype1", pathsubtype);

        mDataBaseHelper = new DataBaseHelper(PieceRatingActivity.this);
        mDataBase = mDataBaseHelper.getWritableDatabase();

        String s = Integer.toString(howManyUnratedLeft(pathtype, pathsubtype));
        Log.i("Pieces left:", s);

        int i = 1;
        Log.i("if any rating ex", String.valueOf(checkIfAnyRatingExists(pathtype, pathsubtype)));
        if (checkIfAnyRatingExists(pathtype, pathsubtype) == false) {
            int holder = getPiecesCount();
            Log.i("Pieces count:", Integer.toString(holder));
            while (i <= holder) {
                if (checkIfPieceExists(pathtype, pathsubtype, i)) {
                    mPieceObject = getPieceObject(i, pathtype, pathsubtype);

                    break;
                } else i++;
            }
        } else {
            Log.i("getting nearest object", "doing");
            mPieceObject = getPieceObject(pickClosestPiece(pathtype, pathsubtype), pathtype, pathsubtype);
            Log.i("getting nearest object", "done");
        }


        hooksandstuff(pathtype, pathsubtype);
        youtubeStuff();

    }

    private void hooksandstuff(final String pathtype, final String pathsubtype) {


        ttitle = findViewById(R.id.aprt1);
        tcomposer = findViewById(R.id.aprt2);
        tperiod = findViewById(R.id.aprt3);
        ttype = findViewById(R.id.aprt4);
        tdesc = findViewById(R.id.aprt5);

        rtext1 = findViewById(R.id.rtext3);
        rtext2 = findViewById(R.id.rtext4);
        rtext3 = findViewById(R.id.rtext5);


        rbar1 = findViewById(R.id.rratingBar1);
        rbar2 = findViewById(R.id.rratingBar2);
        rbar3 = findViewById(R.id.rratingBar3);
        buttrat1 = findViewById(R.id.butt1);

        if (pathtype.equals("PIECE_TYPE")) {
            rbar2.setVisibility(View.GONE);
            rtext2.setVisibility(View.GONE);
        }


        rbar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar arg0, float rateValue, boolean arg2) {
                Log.d("Rating", "your selected value is :" + rateValue);
            }
        });


        ttitle.setText(mPieceObject.title);
        tcomposer.setText(mPieceObject.composer);
        tperiod.setText(mPieceObject.period + ", ");
        ttype.setText(mPieceObject.type);
        tdesc.setText(mPieceObject.piece_description);

        Log.i(TAG, "rating1");
        buttrat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float r1 = rbar1.getRating();
                float r2 = rbar2.getRating();
                float r3 = rbar3.getRating();

                if (pathtype.equals("PIECE_TYPE") == true) {
                    r2 = -20;
                }
//                Toast.makeText(PieceRatingActivity.this, Boolean.toString(pathtype.equals("PIECE_TYPE")), Toast.LENGTH_SHORT).show();
//                Toast.makeText(PieceRatingActivity.this, Float.toString(r2), Toast.LENGTH_SHORT).show();

                if (r1 == 0 || r2 == 0 || r3 == 0)
                    Toast.makeText(PieceRatingActivity.this, "Please insert rating", Toast.LENGTH_SHORT).show();
                else {


                    mRating = new RatingObject(r1, r2, r3);

//                    Toast.makeText(PieceRatingActivity.this, Float.toString(mRating.rating1)
//                            + Float.toString(mRating.rating2)
//                            + Float.toString(mRating.rating3), Toast.LENGTH_SHORT).show();
                    if (checkIfRatingExists(pathtype, pathsubtype, parseInt(mPieceObject.piece_id)) == false) {

                        saveRating(pathtype, pathsubtype, parseInt(mPieceObject.piece_id), mRating.rating1, mRating.rating2, mRating.rating3);

                        if (howManyUnratedLeft(pathtype, pathsubtype) == 0) {
                            Toast.makeText(PieceRatingActivity.this, "No pieces left to rate", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(PieceRatingActivity.this, MainDashBoard.class);
                            PieceRatingActivity.this.startActivity(myIntent);
                            Log.d("No", "No pieces to rate");
                        } else {
                            Intent myIntent = new Intent(PieceRatingActivity.this, PieceRatingActivity.class);

                            myIntent.putExtra("Type", pathtype);
                            myIntent.putExtra("Subtype", pathsubtype);
                            myIntent.putExtra("PrevId", mPieceObject.piece_id);
                            PieceRatingActivity.this.startActivity(myIntent);
                        }
                    } else
                        Toast.makeText(PieceRatingActivity.this, "Piece already rated", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void youtubeStuff() {

        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubeview);

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {



            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(mPieceObject.youtube_link);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }


        };
        mYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);

}

        private int getPiecesCount () {

            int countpieces = 0;
            String selectQuery = "Select Count(*) FROM PIECES";

            Cursor cursor = mDataBase.rawQuery(selectQuery, null);
            cursor.moveToFirst();

            if (cursor.moveToFirst()) {
                countpieces = cursor.getInt(cursor.getColumnIndex("Count(*)"));
            } else return -1;

            cursor.close();

            return countpieces;
        }

        private int getRandomNumber ( int min, int max){
            return (int) ((Math.random() * (max - min)) + min);
        }

        private PieceObject getPieceObject ( int id, String type, String subtype){

            PieceObject pieceObject;
            String selectQuery = "SELECT * From PIECES Where PIECE_ID=" + id + " AND " + type + " = " + "'" + subtype + "'";
            Cursor cursor = mDataBase.rawQuery(selectQuery, null);
            // Move to first row
            cursor.moveToFirst();
            if (cursor.moveToFirst()) {
                Log.d("getPieceObject", cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_COMPOSER)));
                Log.d("getPieceObject", cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_PERIOD)));
                Log.d("getPieceObject", cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_KEY)));
                Log.d("getPieceObject", cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_TYPE)));
                Log.d("getPieceObject", cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_OPUS)));
                Log.d("getPieceObject", cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_ID)));
                Log.d("getPieceObject", cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.YOUTUBE_LINK)));
                Log.d("getPieceObject", cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_DESCRIPTION)));


                pieceObject = new PieceObject(R.drawable.ludwig,
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_NAME)),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_COMPOSER)),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_PERIOD)),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_KEY)),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_TYPE)).substring(1),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_OPUS)),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_ID)),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.YOUTUBE_LINK)),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_DYNAMICS))
                );
                cursor.close();
                return pieceObject;

            } else cursor.close();
            return null;

        }

        private boolean saveRating (String type, String subtype,int pieceid, float rating1,
        float rating2, float rating3){
            int rating11 = (int) rating1;
            int rating22 = (int) rating2;
            int rating33 = (int) rating3;

            ContentValues cv = new ContentValues();
            cv.put(DataBaseHelper.Strings.SCORES_PIECE_ID, pieceid);
            cv.put(DataBaseHelper.Strings.SCORES_TYPE, type);
            cv.put(DataBaseHelper.Strings.SCORES_SUBTYPE, subtype);
            cv.put(DataBaseHelper.Strings.SCORE_1, rating11);
            cv.put(DataBaseHelper.Strings.SCORE_2, rating22);
            cv.put(DataBaseHelper.Strings.SCORE_3, rating33);

            long insert = mDataBase.insert(DataBaseHelper.Strings.SCORES, null, cv);

            if (insert == -1) {
                return false;
            } else {
                return true;
            }
        }

        private boolean checkIfRatingExists (String type, String subtype,int pieceid){

            ArrayList<DashBoardCardType> arr = new ArrayList<>();
            String selectQuery = "Select * from SCORES where SCORES_TYPE='" + type + "' and SCORES_SUBTYPE='" + subtype + "' and SCORES_PIECE_ID=" + pieceid;

            Cursor cursor = mDataBase.rawQuery(selectQuery, null);
            boolean exists = true;

            Log.i(TAG, "type");


            if (cursor.moveToFirst()) {
                return true;
            } else {

                cursor.close();
                return false;
            }

        }

        private boolean checkIfAnyRatingExists (String type, String subtype){

            ArrayList<DashBoardCardType> arr = new ArrayList<>();
            String selectQuery = "Select * from SCORES where SCORES_TYPE='" + type + "' and SCORES_SUBTYPE='" + subtype + "'";

            Cursor cursor = mDataBase.rawQuery(selectQuery, null);
            boolean exists = true;

            if (cursor.moveToFirst()) {
                return true;
            } else {

                cursor.close();
                return false;
            }

        }

        private boolean checkIfPieceExists (String type, String subtype,int pieceid){

            ArrayList<DashBoardCardType> arr = new ArrayList<>();
            String selectQuery = "Select * from PIECES where  " + type + "='" + subtype + "' and PIECE_ID=" + pieceid;

            Cursor cursor = mDataBase.rawQuery(selectQuery, null);
            boolean exists = true;

            if (cursor.moveToFirst()) {
                return true;
            } else {
                cursor.close();
                return false;
            }

        }

        private int pickClosestPiece (String type, String subtype){
            float[] propValues;

            propValues = getProposedValues(type, subtype);
            int closest_id = getNearestPiece(propValues[0], propValues[1], propValues[2], type, subtype);
            return closest_id;
        }

        private float[] getProposedValues (String type, String subtype){

            ArrayList<PieceObject> arrpiece = new ArrayList<>();
            ArrayList<RatingObject> arrrating = new ArrayList<>();
            float[] propValues = new float[3];
            String selectQuery = "SELECT * FROM PIECES  inner  Join ( Select * FROM SCORES where SCORES_TYPE='" + type + "' and SCORES_SUBTYPE='" + subtype + "')a On PIECES.PIECE_ID=a.SCORES_PIECE_ID Where " + type + "='" + subtype + "' ORDER  BY SCORES_PIECE_ID DESC LIMIT 20";

//        String selectQuery="";
//        if (type.equals("PIECE_TYPE")) {selectQuery = "SELECT * FROM PIECES  inner  Join ( Select * FROM SCORES where SCORES_TYPE='\"+type+\"' and SCORES_SUBTYPE='\"+subtype+\"')a On PIECES.PIECE_ID=a.SCORES_PIECE_ID Where \" + type + \"='\" + subtype + \"' ORDER  BY SCORES_PIECE_ID DESC LIMIT 20 and PIECE_TYPE='"+subtype+"'"; }
//        else if (type.equals("PIECE_COMPOSER")){selectQuery = "SELECT * FROM PIECES  inner  Join ( Select * FROM SCORES where SCORES_TYPE='\"+type+\"' and SCORES_SUBTYPE='\"+subtype+\"')a On PIECES.PIECE_ID=a.SCORES_PIECE_ID Where \" + type + \"='\" + subtype + \"' ORDER  BY SCORES_PIECE_ID DESC LIMIT 20 and PIECE_COMPOSER='"+subtype+"'";}
//        else if (type.equals("PIECE_PERIOD")){selectQuery = "SELECT * FROM PIECES  inner  Join ( Select * FROM SCORES where SCORES_TYPE='\"+type+\"' and SCORES_SUBTYPE='\"+subtype+\"')a On PIECES.PIECE_ID=a.SCORES_PIECE_ID Where \" + type + \"='\" + subtype + \"' ORDER  BY SCORES_PIECE_ID DESC LIMIT 20 and PIECE_PERIOD='"+subtype+"'";}


            float countPieces = 0;
            float key_sum = 0;
            float dynamics_sum = 0;
            float key_avg = 0;
            float dynamics_avg = 0;
            int type_number = 0;
            List<Integer> type_numbers = new ArrayList<Integer>();


            Cursor cursor = mDataBase.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    arrpiece.add(new PieceObject(getResources().getIdentifier(cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_COMPOSER)).toLowerCase(), "drawable", getPackageName()),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_NAME)),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_COMPOSER)),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_PERIOD)),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_KEY)).substring(0, 2),

                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_TYPE)).substring(0, 1),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_OPUS)),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_ID)),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.YOUTUBE_LINK)),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_DYNAMICS))));

                    arrrating.add(new RatingObject(parseFloat(cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.SCORE_1))),
                            parseFloat(cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.SCORE_2))),
                            parseFloat(cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.SCORE_3)))));

                } while (cursor.moveToNext());
            } else return null;


            countPieces = arrpiece.size();
            int i = 0;
            while (i < countPieces) {

                PieceObject mPieceObject = arrpiece.get(i);
                RatingObject mRating = arrrating.get(i);

                int hold = (int) mRating.rating2;

                while (hold > 0) {
                    type_numbers.add(parseInt(mPieceObject.type));
                    hold--;

                }


                key_sum = (parseFloat(mPieceObject.key)) * (mRating.rating1 - 3) / 2;
                Log.i(TAG, String.valueOf(key_sum));
                dynamics_sum = (parseFloat(mPieceObject.piece_dynamics)) * (mRating.rating3 - 3) / 2;
                Log.i(TAG, String.valueOf(dynamics_sum));
                i++;
            }
            Log.i(" getProposedValues", "Before avg");
            key_avg = (key_sum) / countPieces;
            dynamics_avg = (dynamics_sum) / countPieces;
            Log.i(" getProposedValues", "After avg1");
            Log.i(" getProposedValues", Character.toString(subtype.charAt(1)));
            if (type.equals("PIECE_TYPE")) {
                type_number = (int) subtype.charAt(1);

            } else {
                type_number = mostCommon(type_numbers);
            }
            // type_number = mostCommon(type_numbers);

            Log.i(" getProposedValues", "After avg");
            Log.i(TAG, String.valueOf(key_avg));
            Log.i(TAG, String.valueOf(type_number));
            Log.i(TAG, String.valueOf(dynamics_avg));

            propValues[0] = key_avg;
            propValues[1] = (float) type_number;
            propValues[2] = dynamics_avg;

            Log.i(" getProposedValues", " Proposed values:");
            Log.i(" getProposedValues", String.valueOf(propValues[0]));
            Log.i(" getProposedValues", String.valueOf(propValues[1]));
            Log.i(" getProposedValues", String.valueOf(propValues[2]));

            cursor.close();
            return propValues;
        }

        public int howManyUnratedLeft (String type, String subtype){


            int count = 0;

            // String selectQuery = "SELECT   * FROM PIECES   LEFT  Join (Select * FROM SCORES where SCORES_TYPE='"+type+"' and SCORES_SUBTYPE='"+subtype+"')a On PIECES.PIECE_ID=a.SCORES_PIECE_ID where SCORES_TYPE is null";

            String selectQuery = "";
            if (type.equals("PIECE_TYPE")) {
                selectQuery = "SELECT   * FROM PIECES   LEFT  Join (Select * FROM SCORES where SCORES_TYPE='" + type + "' and SCORES_SUBTYPE='" + subtype + "')a On PIECES.PIECE_ID=a.SCORES_PIECE_ID where SCORES_TYPE is null and PIECE_TYPE='" + subtype + "'";
            } else if (type.equals("PIECE_COMPOSER")) {
                selectQuery = "SELECT   * FROM PIECES   LEFT  Join (Select * FROM SCORES where SCORES_TYPE='" + type + "' and SCORES_SUBTYPE='" + subtype + "')a On PIECES.PIECE_ID=a.SCORES_PIECE_ID where SCORES_TYPE is null and PIECE_COMPOSER='" + subtype + "'";
            } else if (type.equals("PIECE_PERIOD")) {
                selectQuery = "SELECT   * FROM PIECES   LEFT  Join (Select * FROM SCORES where SCORES_TYPE='" + type + "' and SCORES_SUBTYPE='" + subtype + "')a On PIECES.PIECE_ID=a.SCORES_PIECE_ID where SCORES_TYPE is null and PIECE_PERIOD='" + subtype + "'";
            }

            Cursor cursor = mDataBase.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    count++;
                } while (cursor.moveToNext());
            } else return 0;


            return count;
        }

        private int getNearestPiece ( float prop1, float prop2, float prop3, String type, String
        subtype){
            int nearestid = -1;

            ArrayList<PieceObject> arrpiece = new ArrayList<>();
            List<float[]> distanceArray = new ArrayList<>();
            String selectQuery = "SELECT * FROM PIECES  LEFT  Join ( Select * FROM SCORES where SCORES_TYPE='" + type + "' and SCORES_SUBTYPE='" + subtype + "')a On PIECES.PIECE_ID=a.SCORES_PIECE_ID Where " + type + "='" + subtype + "' and a.SCORES_PIECE_ID is null";

            Cursor cursor = mDataBase.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    arrpiece.add(new PieceObject(getResources().getIdentifier(cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_COMPOSER)).toLowerCase(), "drawable", getPackageName()),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_NAME)),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_COMPOSER)),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_PERIOD)),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_KEY)).substring(0, 2),

                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_TYPE)).substring(0, 1),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_OPUS)),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_ID)),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.YOUTUBE_LINK)),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PIECE_DYNAMICS))));



                } while (cursor.moveToNext());
            }
            cursor.close();

            int i = 0;
            while (i < arrpiece.size()) {
                PieceObject temp = arrpiece.get(i);
                float[] array = new float[3];
                array[0] = Math.abs(parseFloat(temp.key) - prop1);
                if (type.equals("PIECE_TYPE")) {
                    array[1] = 0;
                } else {
                    if (prop2 == parseFloat(temp.type)) array[1] = 0;
                    else array[1] = 3;
                }

                array[2] = Math.abs(parseFloat(temp.piece_dynamics) - prop3);
                float distance = array[0] + array[1] + array[2];
                float[] tempfloatarray = new float[2];
                tempfloatarray[0] = (parseInt(temp.piece_id));
                tempfloatarray[1] = distance;
                distanceArray.add(tempfloatarray);
                Log.d("getNearestPiece, dist:", String.valueOf(distance));
                i++;
            }

            int ii = 0;
            float minvalue = 1000;


            float[] tempfloatarray1;
            while (ii < distanceArray.size()) {

                tempfloatarray1 = distanceArray.get(ii);
                if (minvalue > tempfloatarray1[1]) {
                    minvalue = tempfloatarray1[1];
                    nearestid = (int) tempfloatarray1[0];

                } else {
                    i = i;
                }
                ii++;
            }

            return nearestid;
        }

        public static <T > T mostCommon(List < T > list) {
            Map<T, Integer> map = new HashMap<>();

            for (T t : list) {
                Integer val = map.get(t);
                map.put(t, val == null ? 1 : val + 1);
            }

            Map.Entry<T, Integer> max = null;

            for (Map.Entry<T, Integer> e : map.entrySet()) {
                if (max == null || e.getValue() > max.getValue())
                    max = e;
            }

            return max.getKey();
        }
    @Override
    public void onBackPressed() {


        Intent myIntent = new Intent(PieceRatingActivity.this, MainDashBoard.class);
        PieceRatingActivity.this.startActivity(myIntent);
        finish();

    }


}
