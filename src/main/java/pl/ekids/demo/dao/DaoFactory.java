package pl.ekids.demo.dao;

import pl.ekids.demo.database.ConnectionFactory;

public class DaoFactory {

    public static FeedbackDao getDao() {
        return new PostgresFeedbackDao(ConnectionFactory.createConnection());
    }
}
