package kz.greetgo.sandbox.register.util.my_batis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomBooleanTypeHandler extends BaseTypeHandler<Boolean> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType)
      throws SQLException {
    if (parameter == null) {
      ps.setNull(i, jdbcType.TYPE_CODE);
    } else {
      ps.setInt(i, parameter ? 1 : 0);
    }
  }

  @Override
  public Boolean getNullableResult(ResultSet rs, String columnName)
      throws SQLException {
    String strVal = rs.getString(columnName);
    return strVal == null ? null : !"0".equals(strVal);
  }

  @Override
  public Boolean getNullableResult(ResultSet rs, int columnIndex)
      throws SQLException {
    String strVal = rs.getString(columnIndex);
    return strVal == null ? null : !"0".equals(strVal);
  }

  @Override
  public Boolean getNullableResult(CallableStatement cs, int columnIndex)
      throws SQLException {
    String strVal = cs.getString(columnIndex);
    return strVal == null ? null : !"0".equals(strVal);
  }
}
