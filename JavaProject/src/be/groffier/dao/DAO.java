package be.groffier.dao;

import java.sql.Connection;

import be.groffier.database.DBConnection;

public abstract class DAO<T> {
	protected Connection connectionString = null;

    public DAO()
    {
        this.connectionString = DBConnection.getConnection();
    }

    public abstract boolean create(T obj);
    public abstract boolean delete(T obj);
    public abstract boolean update(T obj);
    public abstract T find(int id);
}