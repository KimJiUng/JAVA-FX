package dao;

import dto.Member;


public class MemberDao extends Dao {

	public static MemberDao memberDao = new MemberDao();
	
	// 1. ȸ������
	public boolean signup(Member member) {
		try {
			String sql = "insert into member(mid,mpassword,mphone,mcard,mname,mpassport,msince) values(?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, member.getMid());
			ps.setString(2, member.getMpassword());
			ps.setString(3, member.getMphone());
			ps.setString(4, member.getMcard());
			ps.setString(5, member.getMname());
			ps.setString(6, member.getMpassport());
			ps.setString(7, member.getMsince());
			ps.executeUpdate();
			return true;
			
		} catch(Exception e) { System.out.println("ȸ������ SQL ���� : "+ e); }
		return false;
	}
	
	// 2. �ߺ�üũ
	public boolean check(String column,String value) {
		try {
			String sql = "select * from member where "+column+"=\""+value+"\"";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch(Exception e) {System.out.println("�ߺ�üũ ���� : "+e);	}
		return false;
	}
	
	// 3. �α���
	public boolean login(String id, String password) {
		try {
			String sql = "select * from member where mid=? and mpassword=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch(Exception e) { System.out.println("login error : "+ e); }
		return false;
	}
	
	// 4. ȸ����ȣ�� ȸ������ ȣ��
	public Member getMember(int mnum) {
		try {
			String sql = "select * from member where mnum=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, mnum);
			rs = ps.executeQuery();
			if(rs.next()) {
				Member member = new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(6), 
						rs.getString(4), rs.getString(7), rs.getString(5),rs.getString(8),rs.getInt(9));
				return member;
			}
		} catch(Exception e) { System.out.println("ȸ������ ȣ�� ���� : "+ e); }
		return null;
	}
	
	// 5. ȸ�����̵�� ȸ������ ȣ��
	public Member getMember(String id) {
		try {
			String sql = "select * from member where mid=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				Member member = new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(6), 
						rs.getString(4), rs.getString(7), rs.getString(5),rs.getString(8),rs.getInt(9));
				return member;
			}
		} catch(Exception e) { System.out.println("ȸ������ ȣ�� ���� : "+ e); }
		return null;
	}
	
	// 6. ���̵� ã��
	public String findid(String name, String phone) {
		try {
			String sql = "select * from member where mname=? and mphone=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, phone);
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getString(2);
			}
		} catch(Exception e) { System.out.println("���̵� ã�� ���� : " + e); }
		return null;
	}
	// 6. ��й�ȣ ã��
	public String findpw(String name,String id, String phone) {
		try {
			String sql = "select * from member where mname=? and mid=? and mphone=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, id);
			ps.setString(3, phone);
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getString(3);
			}
		} catch(Exception e) { System.out.println("��й�ȣ ã�� ���� : " + e); }
		return null;
	}

	
	
}