package com.kediri.humas.sambutan;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
public class SessionManagement{

    Editor editor;
    Context _context;
    private SharedPreferences pref;
    private String username ="username";
    private String id_user  ="id_user";
    private String nama  ="nama";
    private String group  ="group";
    private String opd  ="opd";
    private String login    ="login";

    public SessionManagement(Context context){
        this._context=context;
        pref = _context.getSharedPreferences(login, 0);
        editor=pref.edit();
    }
    public void simpan_session(String id_user, String nama, String username, String group, String opd){
        editor.putString(this.id_user, id_user);
        editor.putString(this.username, username);
        editor.putString(this.nama, nama);
        editor.putString(this.group, group);
        editor.putString(this.opd, opd);
        editor.commit();
    }

    public void hapus_session(){
        editor.clear().commit();
    }

    public String lihat_nama(){
        String namaku=pref.getString(this.nama,"");
        return namaku;
    }

    public String lihat_group(){
        String groupku=pref.getString(this.group,"");
        return groupku;
    }

    public String lihat_opd(){
        String opdku=pref.getString(this.opd,"");
        return opdku;
    }

    public String get_id_user(){
        String id_user=pref.getString(this.id_user,"");
        return id_user;
    }

    public boolean cek_session(){
        String user	=pref.getString(this.id_user,"");
        if(user.equals("")){
            return false;
        }else{
            return true;
        }
    }
}
