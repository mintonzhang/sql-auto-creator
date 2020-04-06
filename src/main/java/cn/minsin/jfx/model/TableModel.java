package cn.minsin.jfx.model;

import cn.minsin.core.tools.StringUtil;

import java.util.List;

public class TableModel {

    private static final long serialVersionUID = -3655204293985204564L;

    private String tableName;

    private String remark;

    private List<Column> column;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Column> getColumn() {
        return column;
    }

    public void setColumn(List<Column> column) {
        this.column = column;
    }

    private String getWithoutFirst() {
        if (StringUtil.isBlank(tableName)) {
            return "";
        }
        String[] split = tableName.split("_");
        return StringUtil.replaceUnderline(split);
    }

    /**
     * 获取生成之后的实体类名称
     *
     * @return
     */
    public String getGTableName() {
        return getWithoutFirst() + "Model";
    }

    /**
     * 获取生成之后的实体类名称
     *
     * @return
     */
    public String getGServiceName() {
        return getWithoutFirst() + "Service";
    }

    /**
     * 获取生成之后的实体类名称
     *
     * @return
     */
    public String getGControllerName() {
        return getWithoutFirst() + "Controller";
    }

    public class Column  {
        /**
         *
         */
        private static final long serialVersionUID = 7347406368753293618L;

        private String column;

        private String type;

        private String length;

        private boolean isNull = true;

        private boolean isPrimaryKey = false;

        private boolean isIncrease = false;

        private Object defaultValue;

        private String description;

        private String remark;


        public String getDescription() {
            String s = StringUtil.removeFormat(description);
            return "描述: " + (s == null ? "无" : s);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLength() {
            if (length == null) {
                return "";
            }
            length = length.replace("）", ")");
            length = length.replace("（", "(");
            if (length.contains(")")) {
                return length;
            }

            return "(" + length + ")";
        }

        public void setLength(String length) {
            this.length = length;
        }

        public boolean isNull() {
            return isNull;
        }

        public void setNull(boolean isNull) {
            this.isNull = isNull;
        }

        public boolean isPrimaryKey() {
            return isPrimaryKey;
        }

        public void setPrimaryKey(boolean isPrimaryKey) {
            this.isPrimaryKey = isPrimaryKey;
        }

        public boolean isIncrease() {
            return isIncrease;
        }

        public void setIncrease(boolean isIncrease) {
            this.isIncrease = isIncrease;
        }

        public Object getDefaultValue() {
            return defaultValue == null ? "" : " DEFAULT " + defaultValue;
        }

        public void setDefaultValue(Object defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getRemark() {
            String s = StringUtil.removeFormat(remark);
            return "备注: " + (s == null ? "无" : s);
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public String getCreateSql(String charSet) {
        StringBuilder sb = new StringBuilder();
        // DROP TABLE IF EXISTS `"+tableName+"`;
        sb.append("CREATE TABLE `").append(tableName).append("` (");
        Column pk = null;
        for (Column c : column) {
            if (c.isPrimaryKey()) {
                pk = c;
            }
            sb.append("`").append(c.getColumn()).append("`").append(c.getType())
                    .append(StringUtil.isBlank(c.getLength()) ? " " : c.getLength())
                    .append(c.isNull() ? " NULL " : " NOT NULL ").append(c.isIncrease() ? " AUTO_INCREMENT " : "")
                    .append(c.getDefaultValue()).append(" COMMENT ").append("'");
            sb.append(c.getDescription()).append(" / ").append(c.getRemark()).append("',");
        }
        if (pk != null) {
            sb.append("PRIMARY KEY (`").append(pk.column).append("`)");
        }
        sb.append(") comment='").append(this.getRemark()).append("'");
        sb.append(" ENGINE=InnoDB DEFAULT CHARACTER SET=`").append(charSet).append("` COLLATE=`").append(charSet).append("_general_ci` ROW_FORMAT=DYNAMIC;");
        return sb.toString();
    }

    public String getDropSql() {
        return " DROP TABLE IF EXISTS `" + tableName + "`;";
    }

}
