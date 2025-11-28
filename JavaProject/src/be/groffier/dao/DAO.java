package be.groffier.dao;

import java.sql.Connection;

public abstract class DAO<T> {
	protected Connection connectionString = null;

    public DAO(Connection connectionString)
    {
        this.connectionString = connectionString;
    }

    public abstract boolean create(T obj);
    public abstract boolean delete(T obj);
    public abstract boolean update(T obj);
    public abstract T find(int id);
}