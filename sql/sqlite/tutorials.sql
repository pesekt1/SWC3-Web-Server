CREATE TABLE IF NOT EXISTS tutorials (
                                         id integer PRIMARY KEY,
                                         description text NOT NULL,
                                         title text NOT NULL,
                                         published integer DEFAULT 0
) WITHOUT ROWID;