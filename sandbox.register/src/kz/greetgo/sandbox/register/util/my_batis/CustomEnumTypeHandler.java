package kz.greetgo.sandbox.register.util.my_batis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

  private Class<E> type;

  public CustomEnumTypeHandler(Class<E> type) {
    if (type == null) {
      throw new IllegalArgumentException("Type argument cannot be null");
    }
    this.type = type;
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType)
      throws SQLException {
    if (jdbcType == null) {
      ps.setString(i, parameter.name());
    } else {
      ps.setObject(i, parameter.name(), jdbcType.TYPE_CODE); // see r3589
    }
  }

  @Override
  public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return valueOf(type, rs.getString(columnName));
  }

  private static <E extends Enum<E>> E valueOf(Class<E> type, String str) {
    if (str == null) {
      return null;
    }
    try {
      return Enum.valueOf(type, str);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  @Override
  public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return valueOf(type, rs.getString(columnIndex));
  }

  @Override
  public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return valueOf(type, cs.getString(columnIndex));
  }
}