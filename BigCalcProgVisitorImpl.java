/**
 * @author Trayan Tsonev
 * @id 12127140
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class BigCalcProgVisitorImpl {
    HashMap<String, BigDecimal> memory = new HashMap<>();

    @Override
    public BigDecimal visitProgram(BigCalcProgParser.ProgramContext ctx) {
        int stmts = ctx.statement().size();
        for (int i = 0; i < stmts-1; i++) {
            visit(ctx.statement(i));
        }
	    return visit(ctx.statement(stmts-1));       
    }

    @Override
    public BigDecimal visitAssignStatement(BigCalcProgParser.AssignStatementContext ctx) {
        String lhs = ctx.Variable().getText();     
        BigDecimal rhs = visit(ctx.expression());
	    memory.put(lhs, rhs);
        return rhs;
    }

    @Override
    public BigDecimal visitExpressionStatement(BigCalcProgParser.ExpressionStatementContext ctx) {
	    return visit(ctx.expression());
    }

    @Override
    public BigDecimal visitIfStatement(BigCalcProgParser.IfStatementContext ctx) {
	    if (visit(ctx.expression()) == true) 
            return visit(ctx.statement(0));
        else return visit(ctx.statement(1));
    }

    @Override
    public BigDecimal visitMulDiv(BigCalcProgParser.MulDivContext ctx) {
        final BigDecimal left = visit(ctx.expression(0));
        final BigDecimal right = visit(ctx.expression(1));
        if (ctx.op.getText().equals("*")) {
            return left.multiply(right);
        } else {
            return left.divide(right, 10, RoundingMode.HALF_UP);
        }
    }

    @Override
    public BigDecimal visitAddSub(BigCalcProgParser.AddSubContext ctx) {
        final BigDecimal left = visit(ctx.expression(0));
        final BigDecimal right = visit(ctx.expression(1));
        if (ctx.op.getText().equals("+")) {
            return left.add(right);
        } else {
            return left.subtract(right);
        }
    }

    @Override
    public BigDecimal visitNum(BigCalcProgParser.NumContext ctx) {
        return new BigDecimal(ctx.Number().getText());
    }

    @Override
    public BigDecimal visitVar(BigCalcProgParser.VarContext ctx) {
        String varname = ctx.Variable().getText();
        if (memory.containsKey(varname)) {
            return memory.get(varname);
        }
        else return new BigDecimal(0);
    }

    @Override
    public BigDecimal visitParenth(BigCalcProgParser.ParenthContext ctx) {
        return visit(ctx.expression());
    }
}
