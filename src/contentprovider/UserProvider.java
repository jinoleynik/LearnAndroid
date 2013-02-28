package contentprovider;

import java.util.ArrayList;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class UserProvider extends ContentProvider  {
    public static final String PROVIDER_NAME = "usersprovider";
    public static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME + "/users");
    private UriMatcher mUriMatcher;
    private static final int USER = 1;
    private static final int USER_ID = 2;
    public static final String INSERT_USER = "INSERT_USER"; 
    public static final String UPDATE_USER = "UPDATE_USER"; 
    public static final String DELETE_USER = "DELETE_USER"; 
    private ArrayList<String> mData;  
    
   

    @Override
    public int delete(Uri arg0, String selection, String[] arg2) {
        int i = Integer.parseInt(selection);
       mData.remove(i);
        return 1;
    }

    @Override
    public String getType(Uri uri) {
        switch (mUriMatcher.match(uri)){
        case USER:
            return "vnd.android.cursor.dir/vnd.templates.provider.users";
        case USER_ID: 
            return "vnd.android.cursor.item/vnd.templates.provider.users";
        default:
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        mData.add(values.getAsString(INSERT_USER));
        Uri newRow = Uri.parse(CONTENT_URI.toString() + "/" + (mData.size()-1));
        return newRow;
    }

    @Override
    public boolean onCreate() {
        mData = new ArrayList<String>();
        mData.add("Junior");
        mData.add("Middle");
        mData.add("Senior");
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(PROVIDER_NAME, "users", USER);
        mUriMatcher.addURI(PROVIDER_NAME, "users/#", USER_ID);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] arg1, String arg2, String[] arg3,
            String arg4) {       
            switch (mUriMatcher.match(uri)){
                case USER:
                    return new UsersCursor(mData);
                case USER_ID:                
                    int i = Integer.parseInt(uri.getLastPathSegment());
                    return new UsersCursor(new ArrayList<String>(mData.subList(i, i)));
                default:
                    throw new IllegalArgumentException("Unsupported URI: " + uri);
                }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] arg3) {
        int i = Integer.parseInt(selection);
        mData.set(i, values.getAsString(UPDATE_USER));
        return 1;
    }

}
