package cn.swingz.store;

import cn.swingz.entities.T1;
import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.resources.SpringResource;
import org.jetbrains.annotations.Nullable;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class T1Store implements CacheStore<Integer, T1> {

    @SpringResource(resourceName = "ds_t1")
    private DataSource dataSource;


    @Override
    public void loadCache(IgniteBiInClosure<Integer, T1> igniteBiInClosure, @Nullable Object... objects) throws CacheLoaderException {
        System.out.println(">> Loading t1cache ...");
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement st = conn.prepareStatement("select * from t1");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                T1 t1 = new T1(rs.getInt(1), rs.getString(2));
                igniteBiInClosure.apply(rs.getInt(1),t1);
            }
        }catch (SQLException e){
            e.printStackTrace();
//            throw new CacheLoaderException("Failed to load t1cache.", e);
        }
    }

    @Override
    public void sessionEnd(boolean b) throws CacheWriterException {

    }


    @Override
    public T1 load(Integer key) throws CacheLoaderException {
        System.out.println(">> Loading t1cache by key ...");
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("select * from t1 where id = ?")) {
                st.setString(1, key.toString());
                ResultSet rs = st.executeQuery();
                return rs.next() ? new T1(rs.getInt(1), rs.getString(2)) : null;
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load values from t1cache", e);

        }
    }

    @Override
    public Map<Integer, T1> loadAll(Iterable<? extends Integer> keys) throws CacheLoaderException {
        return null;
    }

    @Override
    public void write(Cache.Entry<? extends Integer, ? extends T1> entry) throws CacheWriterException {
        System.out.println(">> now insert or update t1's element");
        try (Connection conn = dataSource.getConnection()) {
            String sql = null;
            //mysql
            //sql = "replace into t1(id,type_name) VALUE (?,?)";
           //postgres
            sql = "insert into t1(id,type_name) VALUES (?,?) on conflict(id) do update set type_name=?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt((int)1,entry.getValue().getId());
            st.setString((int)2,entry.getValue().getTypeName());
            //postgres
            st.setString((int)3,entry.getValue().getTypeName());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to insert or update t1.", e);
        }
    }

    @Override
    public void writeAll(Collection<Cache.Entry<? extends Integer, ? extends T1>> collection) throws CacheWriterException {

    }

    @Override
    public void delete(Object o) throws CacheWriterException {
        System.out.println(">> now delete t1's element");
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement st = conn.prepareStatement("delete from t1 where id = ?");
            st.setInt(1,Integer.parseInt(o.toString()));
            st.execute();
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed delete from t1.", e);
        }
    }

    @Override
    public void deleteAll(Collection<?> collection) throws CacheWriterException {

    }
}
