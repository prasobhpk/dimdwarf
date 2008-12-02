/*
 * This file is part of Dimdwarf Application Server <http://dimdwarf.sourceforge.net/>
 *
 * Copyright (c) 2008, Esko Luontola. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *
 *     * Neither the name of the copyright holder nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.orfjackal.dimdwarf.db;

import net.orfjackal.dimdwarf.tx.Transaction;

/**
 * @author Esko Luontola
 * @since 2.12.2008
 */
public class DatabaseTableWithMetadataImpl<K, V> implements DatabaseTableWithMetadata<K, V> {

    private final DatabaseManager dbms;
    private final Transaction tx;

    public DatabaseTableWithMetadataImpl(DatabaseManager dbms, Transaction tx) {
        this.dbms = dbms;
        this.tx = tx;
    }

    public Blob readMetadata(K key, String property) {
        throw new IllegalArgumentException("No such key: " + key);
    }

    public void updateMetadata(K key, String property, Blob value) {
        throw new IllegalArgumentException("No such key: " + key);
    }

    public void deleteMetadata(K key, String property) {
        throw new IllegalArgumentException("No such key: " + key);
    }

    public V read(K key) {
        return null;
    }

    public void update(K key, V value) {
    }

    public void delete(K key) {
    }

    public K firstKey() {
        return null;
    }

    public K nextKeyAfter(K currentKey) {
        return null;
    }
}