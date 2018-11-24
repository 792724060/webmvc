package cn.gin.webmvc.controller.model;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;

public class Model<K, V> extends HashMap<K, V> {

    private static final long serialVersionUID = 1L;

    private boolean update;
    private List<Operation> operations;

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public List<Operation> getOperations() {

        if (operations == null) {
            operations = Lists.newArrayList();
        }

        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public V put(K key, V value) {

        setUpdate(true);
        getOperations().add(new Operation(1, (String) key));

        return super.put(key, value);
    }

    @Override
    public V remove(Object key) {

        setUpdate(true);
        getOperations().add(new Operation(0, (String) key));

        return super.remove(key);
    }

    /**
     * <p>The operation of the current Map.</p>
     * <p>Typically, the Model maintains an operation queue that belongs to the current object, and whenever
     * an element is added, modified or deleted, a record is inserted into the action queue. The corresponding
     * domain objects can be modified according to the operation queue.</p>
     *
     * <ul>
     *   <li>id = 0: remove an attribute.</li>
     *   <li>id = 1: add or update an attribute.</li>
     * </ul>
     */
    public class Operation {

        private int id;
        private String key;

        public Operation(int id, String key) {
            super();
            this.id = id;
            this.key = key;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}