// Copyright © 2008-2009, Esko Luontola. All Rights Reserved.
// This software is released under the MIT License.
// The license may be viewed at http://dimdwarf.sourceforge.net/LICENSE

package net.orfjackal.dimdwarf.db;

import jdave.*;
import jdave.junit4.JDaveRunner;
import net.orfjackal.dimdwarf.db.inmemory.InMemoryDatabaseManager;
import net.orfjackal.dimdwarf.tx.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

/**
 * @author Esko Luontola
 * @since 2.12.2008
 */
@RunWith(JDaveRunner.class)
@Group({"fast"})
public class DatabaseEntryMetadataSpec extends Specification<Object> {

    private static final String TABLE = "test";

    private DatabaseManager dbms;
    private Logger txLogger;

    private Database<Blob, Blob> db;
    private DatabaseTable<Blob, Blob> backingDataTable;
    private DatabaseTable<Blob, Blob> backingMetaTable;
    private DatabaseTableWithMetadata<Blob, Blob> table;

    private Blob key = Blob.fromBytes(new byte[]{0x10});
    private Blob value1 = Blob.fromBytes(new byte[]{0x11});
    private Blob value2 = Blob.fromBytes(new byte[]{0x12});
    private Blob otherKey = Blob.fromBytes(new byte[]{0x20});

    private String propKey = "prop";
    private Blob propValue1 = Blob.fromBytes(new byte[]{0x01});
    private Blob propValue2 = Blob.fromBytes(new byte[]{0x02});

    public void create() throws Exception {
        dbms = new InMemoryDatabaseManager();
        txLogger = mock(Logger.class);
        updateInNewTransaction(TABLE, key, value1);

        TransactionCoordinator tx = new TransactionImpl(txLogger);
        db = dbms.openConnection(tx.getTransaction());
        backingDataTable = db.openTable(TABLE);
        backingMetaTable = db.openTable(TABLE + DatabaseTableWithMetadata.META_SEPARATOR + propKey);
        table = new DatabaseTableWithMetadataImpl<Blob, Blob>(db, TABLE);
    }

    private void updateInNewTransaction(String table, Blob key, Blob value) {
        TransactionCoordinator tx = new TransactionImpl(txLogger);
        dbms.openConnection(tx.getTransaction()).openTable(table).update(key, value);
        tx.prepareAndCommit();
    }


    public class WhenAnEntryDoesNotExist {

        public void theEntryDoesNotExist() {
            specify(table.exists(otherKey), should.equal(false));
        }

        public void itsMetadataCanNotBeRead() {
            specify(new Block() {
                public void run() throws Throwable {
                    table.readMetadata(otherKey, propKey);
                }
            }, should.raise(IllegalArgumentException.class));
        }

        public void itsMetadataCanNotBeUpdated() {
            specify(new Block() {
                public void run() throws Throwable {
                    table.updateMetadata(otherKey, propKey, propValue1);
                }
            }, should.raise(IllegalArgumentException.class));
        }

        public void itsMetadataCanNotBeDeleted() {
            specify(new Block() {
                public void run() throws Throwable {
                    table.deleteMetadata(otherKey, propKey);
                }
            }, should.raise(IllegalArgumentException.class));
        }
    }

    public class WhenAnEntryExists {

        public void theEntryExists() {
            specify(table.exists(key), should.equal(true));
        }

        public void theEntryMayBeRead() {
            specify(table.read(key), should.equal(value1));
        }

        public void theEntryMayBeUpdated() {
            table.update(key, value2);
            specify(backingDataTable.read(key), should.equal(value2));
        }

        public void theEntryMayBeDeleted() {
            table.delete(key);
            specify(backingDataTable.read(key), should.equal(Blob.EMPTY_BLOB));
        }

        public void tableKeysMayBeIterated() {
            specify(table.firstKey(), should.equal(key));
            specify(table.nextKeyAfter(Blob.EMPTY_BLOB), should.equal(key));
            specify(table.nextKeyAfter(key), should.equal(null));
        }
    }

    public class WhenAnEntryHasNoMetadata {

        public void itHasNoMetadata() {
            specify(table.readMetadata(key, propKey), should.equal(Blob.EMPTY_BLOB));
        }
    }

    public class WhenAnEntryHasSomeMetadata {

        public void create() {
            backingMetaTable.update(key, propValue1);
        }

        public void theMetadataMayBeRead() {
            specify(table.readMetadata(key, propKey), should.equal(propValue1));
        }

        public void theMetadataMayBeUpdated() {
            table.updateMetadata(key, propKey, propValue2);
            specify(backingMetaTable.read(key), should.equal(propValue2));
        }

        public void theMetadataMayBeDeleted() {
            table.deleteMetadata(key, propKey);
            specify(backingMetaTable.read(key), should.equal(Blob.EMPTY_BLOB));
        }
    }

    public class WhenAnEntryIsDeleted {
        private DatabaseTable<Blob, Blob> otherTable;

        public void create() {
            backingMetaTable.update(key, propValue1);
            backingDataTable.update(otherKey, value2);
            backingMetaTable.update(otherKey, propValue2);
            otherTable = db.openTable("otherTable");
            otherTable.update(key, value1);
            table.delete(key);
        }

        public void itsMetadataIsAlsoDeleted() {
            specify(backingMetaTable.read(key), should.equal(Blob.EMPTY_BLOB));
        }

        public void theMetadataOfOtherKeysIsUnaffected() {
            specify(backingMetaTable.read(otherKey), should.equal(propValue2));
        }

        public void entriesWithTheSameKeyInOtherTablesAreUnaffected() {
            specify(otherTable.read(key), should.equal(value1));
        }
    }
}
