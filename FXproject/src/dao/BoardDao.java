package dao;

import dto.Board;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BoardDao extends Dao{

	public static BoardDao boardDao = new BoardDao(); // Boarddao class�� ���� ���·� �޸𸮿� ����
	String sql;
	
	// 1. �۾��� �޼���
	public boolean wrtite(Board board) {
		try {
			sql = "insert board(mnum, btitle, bcontent, blocation, bsnapshoturl, bimgurl, mid) values(?, ?, ?, ?, ?, ?, ?)";
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, board.getMnum());
			ps.setString(2, board.getBtitle());
			ps.setString(3, board.getBcontent());
			ps.setString(4, board.getBlocation());
			ps.setString(5, board.getBsnapshoturl());
			ps.setString(6, board.getBimgurl());
			ps.setString(7, board.getMid());
			ps.executeUpdate();
			return true;
		} catch (Exception e) {System.out.println("BoardDao_wrtite_method_exception : "+e);}
		return false;
	}
	
	// 2. ��ü �� ��� �������� �޼���
	public ObservableList<Board> list(String title)	{
		// * ����Ʈ ����
		
		ObservableList<Board> boardlist = FXCollections.observableArrayList();
		try {
			if(title==null) {
				sql = "select * from test.board";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()) {	// ���� ���ڵ尡 ���� �� ���� �ݺ�
					Board board = new Board(rs.getInt(1), rs.getInt(2), rs.getString(3), 
							rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10));
					boardlist.add(board);
				}
			}else {
				sql = "select * from board where btitle like '%"+title+"%' order by bnum desc";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()) {	// ���� ���ڵ尡 ���� �� ���� �ݺ�
					Board board = new Board(rs.getInt(1), rs.getInt(2), rs.getString(3), 
							rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10));
					boardlist.add(board);
				}
			}
			return boardlist;
		} catch (Exception e) {System.out.println("BoadrdDao_list_method_exception : "+e);}
		return null;
	}
	// 3. �� ���� �޼���
	public void delete(int bnum) {
		try {
			sql = "delete from board where bnum=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, bnum);
			ps.executeUpdate();
		} catch (Exception e) {System.out.println("BoardDao_delete_method_exception : "+e);}
	}
	
	// 4. �� ���� �޼���
	public boolean update(Board board) {
		try {
			sql = "update board set btitle=?, blocation=?, bsnapshoturl=?, bimgurl=? where bnum=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, board.getBtitle());
			ps.setString(2, board.getBlocation());
			ps.setString(3, board.getBsnapshoturl());
			ps.setString(4, board.getBimgurl());
			ps.setInt(5, board.getBnum());
			ps.executeUpdate();
			return true;
		} catch (Exception e) {System.out.println("BoardDao_update_method_exception : "+e);}
		return false;
	}
	
	// 5. �� ã�� �޼���	-> ��ü �� ã�� �޼��忡 �μ��� �����ָ� ã����
	
	
	
	
}