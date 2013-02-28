package contentprovider;

import java.util.ArrayList;

import android.database.AbstractCursor;

public class UsersCursor extends AbstractCursor {
    // ID
    public final static int USER_ID_ID = 0;
    public final static String USER_ID = "_id";
    // Name
    public final static int USER_NAME_ID = 1;
    public final static String USER_NAME = "User_name";
    private ArrayList<String> mData;

    public UsersCursor(ArrayList<String> arrdata) {
        mData = arrdata;
    }

    @Override
    public String[] getColumnNames() {
        return new String[] { USER_ID, USER_NAME };
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public double getDouble(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getFloat(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getInt(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long getLong(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public short getShort(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getString(int arg0) {
        switch (arg0) {
        case USER_ID_ID:
            if ((this.mPos < 0) || (mPos >= mData.size())) {
                return null;
            } else {
                return String.valueOf(mPos);
            }
        case USER_NAME_ID:
            return mData.get(mPos);

        default:
            return null;
        }
    }

    @Override
    public boolean isNull(int arg0) {
        return !(mData.get(mPos).isEmpty());
    }
    
    
   
}
