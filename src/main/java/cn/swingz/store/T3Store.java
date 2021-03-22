package cn.swingz.store;

import cn.swingz.entities.T1;
import cn.swingz.entities.T3;
import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.resources.SpringResource;
import org.jetbrains.annotations.Nullable;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

public class T3Store implements CacheStore<String, T3> {

    @SpringResource(resourceName = "ds_t2")
    private DataSource dataSource;


    @Override
    public void loadCache(IgniteBiInClosure<String, T3> igniteBiInClosure, @Nullable Object... objects) throws CacheLoaderException {
        System.out.println(">> Loading t3 ccache from cn.swingz.store...");
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement st = conn.prepareStatement("select * from t3");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                T3 t3 = new T3(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4));
                igniteBiInClosure.apply(t3.getParentId()+" "+t3.getKey(),t3);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void sessionEnd(boolean b) throws CacheWriterException {

    }


    @Override
    public T3 load(String key) throws CacheLoaderException {
        System.out.println(">> Loading t1cache by key ...");
        try (Connection conn = dataSource.getConnection()) {
            String sql = null;
            //mysql
            // sql = "select * from t3 where `parent_id` = ? and `key` = ?";
            //postgres
            sql = "select * from t3 where parent_id = ? and key = ?";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setString(1, key.toString().split(" ")[0]);
                st.setString(2, key.toString().split(" ")[1]);
                ResultSet rs = st.executeQuery();
                return rs.next() ? new T3(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4)) : null;
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load values from t1cache", e);

        }
    }

    @Override
    public Map<String, T3> loadAll(Iterable<? extends String> keys) throws CacheLoaderException {
        return null;
    }

    @Override
    public void write(Cache.Entry<? extends String, ? extends T3> entry) throws CacheWriterException {
        System.out.println(">> now insert or update t3's element");
        try (Connection conn = dataSource.getConnection()) {
            String sql = null;
            //mysql
            // sql = "replace into t3(parent_id,`key`,`value`,remarks) VALUE (?,?,?,?)";
            //postgres
            sql = "insert into t3(parent_id,key,value,remarks) VALUES (?,?,?,?) on " +
                    "conflict(parent_id,key) do update set value = ?,remarks = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1,entry.getValue().getParentId());
            st.setString(2,entry.getValue().getKey());
            st.setString(3,entry.getValue().getValue());
            st.setString(4,entry.getValue().getRemarks());
            //postgres
            st.setString(5,entry.getValue().getValue());
            st.setString(6,entry.getValue().getRemarks());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeAll(Collection<Cache.Entry<? extends String, ? extends T3>> collection) throws CacheWriterException {

    }

    @Override
    public void delete(Object o) throws CacheWriterException {
        System.out.println(">> now delete t3's element by parent_id");
        try (Connection conn = dataSource.getConnection()) {
            String sql = null;
            //mysql
            // sql = "delete from t3 where `parent_id` = ? and `key` = ?";
            //postgres
            sql = "delete from t3 where parent_id = ? and key = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1,Integer.parseInt(o.toString().split(" ")[0]));
            st.setString(2,o.toString().split(" ")[1]);
            st.execute();
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed delete from t1.", e);
        }
    }

    @Override
    public void deleteAll(Collection<?> collection) throws CacheWriterException {

    }
}
