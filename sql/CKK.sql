-- CKK DATABASE

-- CKK계정 생성
--============system 계정으로 실행해주세요==========================
--  create user CKK
--  identified by CKK
--  default tablespace users;
--  grant connect, resource to CKK;
--  grant create any job to CKK;
--===================================================================


--==========================TABLE목록============================
--  ALL_USER : 회원
--  QUIT_USER : 탈퇴한 회원
--  TUTOR_RESUME : 튜터 이력서 관리
--  TUTOR_CERTIFICATES : 튜터 증명서 / 자격증 고유코드
--  COMMITION_GRADE : 등급
--  POINT_LOG : 포인트 입출력 기록
--  CS_BOARD : 고객센터 게시판
--  CS_ASW_BOARD : 고객센터 게시판 답변
--  CLASS : 클래스 게시판 모집글
--  CLASS_IMAGES : 클래스 - 첨부이미지
--  CLASS_SCHEDULE : 클래스 - 스케쥴
--  REVIEW : 리뷰 게시글
--=================================================================

--==========================SEQUENCE목록=========================
-- SEQ_POINT_LOG; --: 포인트 로그 시퀀스
-- SEQ_CS_BOARD_NO; --: 고객센터 게시글 고유 번호 시퀀스 
-- SEQ_CLASS_NO; --: 클래스 게시글 고유 번호 시퀀스
-- SEQ_CS_NO; --: 클래스 스케쥴 고유 번호 시퀀스
-- SEQ_MSG_NO; --:


--==================================================================

--==========================TRIGGER목록============================
--  TRG_QUIT_USER;-- : 탈퇴회원 관리 트리거
--  TRG_POINT_LOG_IO;-- : 포인트 입출력 트리거
--  TRG_CS_ASW_YN : 고객센터 게시판 답변여부 트리거

--==================================================================

--============================PROCEDURE==========================
--  PROC_APPLY_END_CK : 수강신청 마감 여부 확인 프로시져
--  PROC_CLASS_END_CK  : 수업 종료 여부 확인 프로시져
--==================================================================

--==================================================================
-- DROP TABLE ALL_USER;
-- DROP TABLE QUIT_USER;
-- DROP TABLE CLASS_IMAGES;
-- DROP TABLE CLASS;
-- DROP TABLE CLASS_SCHEDULE;
-- DROP TABLE REVIEW;
-- DROP TABLE CS_ASW_BOARD;
-- DROP TABLE CS_BOARD;
-- DROP TABLE POINT_LOG;
-- DROP TABLE COMMISION_GRADE;
-- DROP TABLE TUTOR_CERTIFICATES;
-- DROP TABLE TUTOR_RESUME;
-- DROP TABLE MESSAGE;

 SELECT * FROM ALL_USER;
-- SELECT * FROM QUIT_USER;
 SELECT * FROM CLASS;
-- SELECT * FROM CLASS_IMAGES;
 SELECT * FROM CLASS_SCHEDULE;
 SELECT * FROM REVIEW;
-- SELECT * FROM CS_ASW_BOARD;
-- SELECT * FROM CS_BOARD;
-- SELECT * FROM POINT_LOG;
-- SELECT * FROM COMMISION_GRADE;
-- SELECT * FROM TUTOR_CERTIFICATES;
 SELECT * FROM TUTOR_RESUME;
--================================================================

delete from review where user_id = 'yoogs';
commit;

--================================================================
--                            CONTENTS : TABLES 
-- TABLE NAME(ABBR) : INFO : TRG_YS/YO/N : SEQ_Y/N
--  테이블 이름(약자) : 정보 : 트리거 주/객 여부 : 시퀀스 사용 여부
--================================================================

-- 수수료 등급 :수수료 퍼센트와 수수료 등급 관리 테이블 | 트리거 대입 없음 | 시퀀스 대입 없음
CREATE TABLE COMMISION_GRADE (
	COMM_GRADE	 VARCHAR2(2)	NOT NULL,
	COMM	NUMBER NOT NULL,
    
     CONSTRAINT PK_COMM_GRADE_COMG PRIMARY KEY(COMM_GRADE),
     CONSTRAINT CK_COMM_GRADE_COMG CHECK (COMM_GRADE IN ('US', 'T1', 'T2', 'T3', 'T4', 'AD')),
     CONSTRAINT CK_COMM_COMG CHECK(COMM IN (0.05, 0.04, 0.03, 0.02, 0.01, 0))
);

--회원 테이블 : 모든 유형의 유저를 위한 기반 테이블 | 시퀀스 대입 없음 
CREATE TABLE ALL_USER (
	USER_ID   VARCHAR2(20)    NOT NULL,
	COMM_GRADE    VARCHAR2(2) ,
	PASSWORD  VARCHAR2(50)    NOT NULL,
	USER_NAME     VARCHAR2(12) 	NOT NULL,
	GENDER    CHAR(1)	NOT NULL,
	BIRTHDAY  DATE    NOT NULL,
	EMAIL     VARCHAR2(30)	 NOT NULL,
	ADDRESS   VARCHAR2(200)	NULL,
	PHONE  VARCHAR2(11) 	NOT NULL,
	ENROLL_DATE   DATE DEFAULT SYSDATE 	NOT NULL,
	POINT_SUM     NUMBER DEFAULT 0  NOT NULL,
	RESUME_YNP    CHAR(1) DEFAULT 'N'  NOT NULL,
    
    CONSTRAINT PK_USER_ID_AU PRIMARY KEY (USER_ID),
    CONSTRAINT UQ_EMAIL_AU UNIQUE (EMAIL),
    CONSTRAINT UQ_PHONE_AU UNIQUE (PHONE),
    CONSTRAINT CK_COMM_GRADE_AU FOREIGN KEY(COMM_GRADE)
                                                  REFERENCES COMMISION_GRADE(COMM_GRADE)
                                                  ON DELETE SET NULL,
    CONSTRAINT CK_GENDER_AU CHECK (GENDER IN ('M', 'F')),
    CONSTRAINT CK_RESUME_YNP_AU CHECK (RESUME_YNP IN('Y', 'N', 'P')),
    CONSTRAINT CK_POINT_SUM_AU CHECK (POINT_SUM >= 0)
);

-- 탈퇴 회원 : 탈퇴한 회원 관리 테이블 | 트리거 객체 ( TRG_QUIT_USER ) | 시퀀스 대입 없음
CREATE TABLE QUIT_USER (
	USER_ID	VARCHAR(20)	NOT NULL,
	GRADE	CHAR(2)	NOT NULL,
	PASSWORD	VARCHAR2(50)	NOT NULL,
	USER_NAME	VARCHAR2(12)	NOT NULL,
	GENDER	CHAR(1)	NOT NULL,
	BIRTHDAY	DATE	NOT NULL,
	EMAIL	VARCHAR2(30)	NOT NULL,
	ADDRESS	VARCHAR2(200)	NULL,
	PHONE  VARCHAR2(11) 	NOT NULL,
	ENROLL_DATE	DATE	DEFAULT SYSDATE NOT NULL,
	QUIT_DATE	DATE	DEFAULT SYSDATE NOT NULL,
	POINT_SUM	NUMBER	NOT NULL
);

-- 튜터 이력서 관리 테이블 : 튜터의 이력서 관리용 테이블 | 트리거 대입 없음 | 시퀀스 대입 없음
CREATE TABLE TUTOR_RESUME (
	TUTOR_ID	VARCHAR2(20)	 NOT NULL,
	AWARD_RECORD	VARCHAR2(200)	NULL,
	CAREER VARCHAR2(500)	NULL,
	PROFILE_ORG	VARCHAR2(100)   DEFAULT 'DEFAULT_T_PHOTO.PNG'	  NULL,
	PROFILE_REN	VARCHAR2(100)	NULL,
    CERT_N_1	VARCHAR2(60)	NULL,
	CERT_C_1	VARCHAR2(60)	NULL,
	CERT_N_2	VARCHAR2(60)	NULL,
	CERT_C_2	VARCHAR2(60)	NULL,
	CERT_N_3	VARCHAR2(60)	NULL,
	CERT_C_3	VARCHAR2(60)	NULL,
    
    CONSTRAINT PK_TUTOR_ID_TUR PRIMARY KEY(TUTOR_ID),
    CONSTRAINT FK_TUTOR_ID_TU FOREIGN KEY(TUTOR_ID)
                                                REFERENCES ALL_USER(USER_ID)
                                                ON DELETE CASCADE
);

-- 포인트 입출력 기록 :포인트 입출력 내역을 기록하는 테이블 : 트리거 대입 있음( TRG_POINT_LOG_IO) | 시퀀스 대입 있음 ( SEQ_POINT_LOG )
CREATE TABLE POINT_LOG (
	LOG_NO	NUMBER	NOT NULL,
	USER_ID	VARCHAR2(20)	NOT NULL,
	POINT_IO	CHAR(1)	NOT NULL,
	POINT_CONTENT	VARCHAR(20)	NOT NULL,
	POINT_AMOUNT	NUMBER	NOT NULL,
	POINT_IO_DATE	DATE DEFAULT SYSDATE	NOT NULL,
    
    CONSTRAINT PK_LOG_NO_PL PRIMARY KEY (LOG_NO),
    CONSTRAINT FK_USER_ID_PL FOREIGN KEY (USER_ID) 
                                        REFERENCES ALL_USER (USER_ID),
    CONSTRAINT CK_POINT_IO_PL CHECK (POINT_IO IN ('I', 'O'))
);

-- 고객센터 게시판 : 고객센터 게시판 글 관리 테이블 : 트리거 없음 : 시퀀스 대입 있음 ( SEQ_CS_BOARD_NO )
CREATE TABLE CS_BOARD (
	CS_NO	NUMBER	NOT NULL,
	USER_ID	VARCHAR2(20) 	NULL,
	CS_TITLE	VARCHAR2(100)	NOT NULL,
	CS_CONTENT	VARCHAR2(3000)	NOT NULL,
	CS_FILE_ORG	VARCHAR2(200)	NULL,
	CS_FILE_REN    VARCHAR2(200)	NULL,
	CS_WRITE_DATE	DATE DEFAULT SYSDATE	NOT NULL,
	CS_ANSWER_YN	CHAR(1) DEFAULT 'N' NOT NULL,
    CS_SECRET_YN    CHAR(1) DEFAULT 'N' NOT NULL,

    CONSTRAINT PK_CS_NO_CB PRIMARY KEY(CS_NO),
    CONSTRAINT CK_CS_ANSWER_YN CHECK (CS_ANSWER_YN IN ('Y', 'N')),
    CONSTRAINT CK_CS_SECREST_YN CHECK (CS_SECRET_YN IN ('Y', 'N'))
);

--고객센터 게시판 답변 :고객센터 답변글을 관리하는 테이블 | 트리거 있음 (TRG_CS_ASW_YN) | 시퀀스 대입 없음
CREATE TABLE CS_ASW_BOARD (
	CS_NO	NUMBER	NOT NULL,
	ADMIN_ID	    VARCHAR2(20)	NOT NULL,
	CSA_CONTENT   VARCHAR2(3000)	NOT NULL,
	CSA_DATE	DATE	DEFAULT SYSDATE NOT NULL,
    
    CONSTRAINT FK_CS_NO_ASW_CAB FOREIGN KEY(CS_NO)
                                            REFERENCES CS_BOARD(CS_NO)
                                            ON DELETE CASCADE
);
 
--클래스 게시판 모집글 : 클래스 게시판의  게시글 관리 테이블 | 트리거 없음 | 시퀀스 대입 있음 ( SEQ_CLASS_NO )
CREATE TABLE CLASS (
	CLASS_NO	NUMBER	NOT NULL,
	TUTOR_ID	VARCHAR2(20)	NOT NULL,
	PRICE	NUMBER	NOT NULL,
	TITLE	VARCHAR2(60)	NOT NULL,
	CLASS_CONTENT	VARCHAR2(3000)	NOT NULL,
	LAST_APPLY_DATE	DATE	NOT NULL,
	CAPACITY	NUMBER	DEFAULT 1 NOT NULL,
	CLASS_ADDRESS 	VARCHAR2(200)	NOT NULL,
	APPLY_EXPIRE_YN	CHAR(1)	DEFAULT 'N' NOT NULL,
	CLASS_LOCATION	CHAR(5)	NOT NULL,
	START_TIME	NUMBER	NOT NULL,
	END_TIME	NUMBER	NOT NULL,
	CLASS_DATE	DATE	NOT NULL,
	CLASS_END_YN	CHAR(1)	NOT NULL,
    CATEGORY	CHAR(2)	NULL,
    
    CONSTRAINT PK_CLASS_NO_CL PRIMARY KEY (CLASS_NO),
    CONSTRAINT FK_TUTOR_ID_CL FOREIGN KEY(TUTOR_ID) 
                                            REFERENCES ALL_USER(USER_ID)
                                            ON DELETE CASCADE,
    CONSTRAINT CK_APPLY_EXPIRE_YN_CL CHECK (APPLY_EXPIRE_YN IN ('Y', 'N')),
    CONSTRAINT CK_CLASS_LOCATION_CL CHECK (CLASS_LOCATION IN ('NORTH', 'SOUTH', 'EAST', 'WEST')),
    CONSTRAINT CK_CLASS_END_YN_CL CHECK (CLASS_END_YN IN ('Y' , 'N')),
    CONSTRAINT CK_PRICE_CL CHECK (PRICE >= 0 ),
    CONSTRAINT CK_CATEGORY_CL CHECK(CATEGORY IN('MD','DS','DR','AL'))
);

--클래스 첨부 이미지 : 클래스 게시판 게시글에 사용될 이미지 관리 테이블 | 트리거 없음 | 시퀀스 없음
CREATE TABLE "CLASS_IMAGES" (
	"CLASS_NO"	NUMBER	NOT NULL,
	"CLASS_PIC1_ORG"	VARCHAR2(100)	NOT NULL,
	"CLASS_PIC1_REN"	    VARCHAR2(100)	NOT NULL,
	"CLASS_PIC2_ORG"	VARCHAR2(100)	NULL,
	"CLASS_PIC2_REN"    	VARCHAR2(100)	NULL,
	"CLASS_PIC3_ORG"	VARCHAR2(100)	NULL,
	"CLASS_PIC3_REN"	    VARCHAR2(100)	NULL,
    
    CONSTRAINT "PK_CLASS_NO_CL_IMG" PRIMARY KEY ("CLASS_NO"),
    CONSTRAINT "FK_CLASS_NO_CL_IMG" FOREIGN KEY ("CLASS_NO") 
                                                      REFERENCES "CLASS"("CLASS_NO") 
                                                      ON DELETE CASCADE
);


--클래스 스케쥴 : 스케줄-게시글 관리 테이블 | 트리거 대입 없음 | 시퀀스 대입 있음 ( SEQ_CLASS_SCH_NO )
CREATE TABLE "CLASS_SCHEDULE" (
    "SCHEDULE_NO" NUMBER NOT NULL,
	"CLASS_NO"	NUMBER	NOT NULL,
	"USER_ID"	VARCHAR2(20)	NOT NULL,
    
    CONSTRAINT "FK_CLASS_NO_CLS_SCH" FOREIGN KEY ("CLASS_NO") 
                                                    REFERENCES "CLASS"("CLASS_NO") 
                                                    ON DELETE CASCADE,
    CONSTRAINT "FK_USER_ID_CLS_SCH" FOREIGN KEY ("USER_ID") 
                                                    REFERENCES "ALL_USER"("USER_ID") 
                                                    ON DELETE CASCADE,
    CONSTRAINT "PK_SCHEDULE_NO_SCH" PRIMARY KEY("SCHEDULE_NO")
);

--리뷰 게시글 : 리뷰 글 관리 테이블 | 트리거 대입 없음 | 시퀀스 대입 없음
CREATE TABLE "REVIEW" (
	"USER_ID"	VARCHAR2(20)	NOT NULL,
	"TUTOR_ID"	VARCHAR2(20)	NOT NULL,
	"CLASS_NO"	NUMBER	NOT NULL,
	"REVIEW_CONTENT"	VARCHAR2(1000)	NULL,
	"REVIEW_SCORE"	NUMBER	NOT NULL,
	"REVIEW_DATE"	DATE	DEFAULT SYSDATE NOT NULL,
    
    CONSTRAINT "PK_UI_TI_CN_REW" PRIMARY KEY("USER_ID","TUTOR_ID","CLASS_NO"),
    CONSTRAINT "FK_USER_ID_REW" FOREIGN KEY("USER_ID") 
                                            REFERENCES "ALL_USER"("USER_ID")
                                            ON DELETE CASCADE,
    CONSTRAINT "FK_TUTOR_ID_REW" FOREIGN KEY("TUTOR_ID") 
                                            REFERENCES "ALL_USER"("USER_ID") 
                                            ON DELETE CASCADE,
    CONSTRAINT "FK_CLASS_NO_REW" FOREIGN KEY("CLASS_NO") 
                                            REFERENCES "CLASS"("CLASS_NO")
);

CREATE TABLE MESSAGE (
    MSG_NO NUMBER,
    USER_ID VARCHAR2(20) NOT NULL,
    MSG_TITLE VARCHAR2(100) NOT NULL,
    MSG_CONTENT VARCHAR2(3000) NOT NULL,
    MSG_DATE DATE DEFAULT SYSDATE NOT NULL,
    MSG_READ_YN CHAR(1) DEFAULT 'N' NOT NULL,
    
    CONSTRAINT PK_MESSAGE_MSG_NO PRIMARY KEY(MSG_NO),
    CONSTRAINT FK_MESSAGE_USER_ID FOREIGN KEY(USER_ID)
                                                 REFERENCES "ALL_USER"("USER_ID")
                                                 ON DELETE CASCADE
);

--================================================================
--                        CONTENTS : SEQUENCES
--                  SEQUENCE NAME(ABBR) : INFO
--                             시퀀스 이름(약자) : 정보
--================================================================

--포인트 로그 시퀀스
CREATE SEQUENCE SEQ_POINT_LOG;

--고객센터 게시글 고유번호 시퀀스
CREATE SEQUENCE SEQ_CS_NO;

--클래스 게시글 고유 번호 시퀀스
CREATE SEQUENCE SEQ_CLASS;

--클래스 스케쥴 고유번호 시퀀스
CREATE SEQUENCE SEQ_SCH_NO;

--메시지 고유번호 시퀀스
CREATE SEQUENCE SEQ_MSG_NO;

--================================================================
--                         CONTENTS : TRIGGERS 
--TRIGGER NAME(ABBR) : SUBJECT : OBJECT : PURPOSE
--                    트리거 이름(약자) : 주체 : 객체 : 목적
--================================================================
-- 탈퇴 트리거 : ALL_USER : QUIT_USER : 탈퇴회원 기록
CREATE OR REPLACE TRIGGER TRG_QUIT_USER
    BEFORE
    DELETE ON ALL_USER
    FOR EACH ROW
BEGIN

  INSERT INTO QUIT_USER VALUES(:old.USER_ID, :old.COMM_GRADE, :old.PASSWORD,
                                                        :old.USER_NAME, :old.GENDER, :old.BIRTHDAY,
                                                        :old.EMAIL, :old.ADDRESS, :old.PHONE, :old.ENROLL_DATE , default, :old.POINT_SUM);
END;
/



--포인트 입출력 트리거 : POINT_LOG : ALL_USER.POINT_AMOUNT : 포인트 총계 갱신
CREATE OR REPLACE TRIGGER TRG_POINT_LOG_IO
    BEFORE
    INSERT ON POINT_LOG
    FOR EACH ROW
BEGIN
    IF :NEW.POINT_IO = 'I'
     THEN
        UPDATE ALL_USER
        SET
            POINT_SUM = POINT_SUM + :NEW.POINT_AMOUNT
        WHERE
            USER_ID = :NEW.USER_ID;
    ELSIF  :NEW.POINT_IO = 'O'
    THEN
        UPDATE ALL_USER
        SET
            POINT_SUM = POINT_SUM - :NEW.POINT_AMOUNT
        WHERE
            USER_ID = :NEW.USER_ID;
    END IF;
END;
/

--관리자 게시판 답변 여부 트리거 : CS_ASW_BOARD : CS_BOARD.CS_ANSWER_YN : 관리자 게시판 답변 여부 확인
CREATE OR REPLACE TRIGGER TRG_CS_ASW_YN
    BEFORE 
    INSERT ON CS_ASW_BOARD
    FOR EACH ROW
DECLARE
        v_user_id all_user.user_id%type;
BEGIN
    UPDATE CS_BOARD SET CS_ANSWER_YN = 'Y' WHERE CS_NO = :NEW.CS_NO;
    select user_id into v_user_id from cs_board where cs_no = :new.cs_no;
    insert into message values(SEQ_MSG_NO.nextval, v_user_id, '고객센터 질문에 관한 메시지입니다.', '올리신 문의에 대한 관리자의 답변이 작성 되었습니다.', default, default);
END;
/

--회원가입시 메세지 전송 트리거 : all_user : message : 회원가입 축하 메세지 전송
create or replace trigger trg_user_enroll
    after
    insert on all_user
    for each row
declare
    v_userId all_user.user_id%type;
begin
    insert into message values (seq_msg_no.nextval,
                                                :new.user_Id, '회원가입을 축하합니다!', 'Cooking-King의 회원이 되신' || :new.user_Id || '님 환영합니다! 오늘의 꿈나무가 내일의 요리왕!',
                                                default, default);
end;
/





--================================================================
--                         CONTENTS : PROCEDURE 
--
--
--================================================================

--수강신청 마감 여부  확인 프로시져 -> 클래스 게시판 마감여부 Y, 튜터에게 포인트 전송
--DROP PROCEDURE PROC_APPLY_END_CK;
create or replace procedure PROC_APPLY_END_CK
is
    v_cno number;
    v_ldate date;
    v_cnt number;
    v_cnt2 number;
    v_prc class.price%type;
    v_tutor class.tutor_id%type;
    v_admin class.tutor_id%type;
    
begin
    select count(*)
    into v_cnt
    from class;
    for i in 1..v_cnt loop
        select class_no, last_apply_date
        into v_cno, v_ldate
        from (
                select class_no, last_apply_date, rownum rnum
                from class)
        where rnum = i;
        
    if(v_ldate < sysdate) then
        update class set apply_expire_yn = 'Y' where class_no = v_cno;
        
          --  여기다가 클래스 수강인원 체크, 가격체크, 튜터 아이디 체크
        select count(*) 
        into v_cnt2 
        from class C 
            inner join class_schedule SC 
                on C.class_no = SC.class_no
        where SC.class_no = v_cno;
        
                
        select PRICE,tutor_id 
        into v_prc, v_tutor 
        from class 
        where class_no = v_cno; --  여기다가 클래스 수강인원 체크, 가격체크, 튜터 아이디 체크
        

        -- 튜터에게 수강인원 * 가격 보내주기 admin 포인트 빼기 =>로그테이블에 2개행 추가
        insert into point_log values (SEQ_POINT_LOG.NEXTVAL, 'admin', 'O', '수강료 지불', (v_prc)*(v_cnt2), default); 
        insert into point_log values (SEQ_POINT_LOG.NEXTVAL, v_tutor, 'I', '수강료 지불', (v_prc)*(v_cnt2), default);
    end if;
    end loop;
end;
/

--수업 종료 여부 확인 프로시져
--DROP PROCEDURE PROC_CLASS_END_CK;
CREATE OR REPLACE PROCEDURE PROC_CLASS_END_CK 
IS
    V_CNO NUMBER;
    V_EDATE DATE;
    V_CNT NUMBER;
    v_applied number;
    v_user_id all_user.user_id%type;
    v_title class.title%type;
BEGIN
    SELECT COUNT(*) INTO V_CNT FROM CLASS;
    
    FOR i IN 1 .. V_CNT LOOP
        SELECT CLASS_NO, CLASS_DATE, title INTO V_CNO, V_EDATE, v_title
        FROM (
            SELECT CLASS_NO, CLASS_DATE, title, ROWNUM RNUM
            FROM CLASS)
        WHERE i = RNUM;
    IF(sysdate > V_EDATE) THEN
        UPDATE CLASS SET CLASS_END_YN = 'Y' WHERE CLASS_NO = V_CNO;
        select count(*) into v_applied from class_schedule where class_no = v_cno;
        for i in 1 .. v_applied loop
            select user_id
            into v_user_id
            from (select user_id, rownum rnum from class_schedule)
            where rnum = i;
            insert into message values(seq_msg_no.nextval, v_user_id, '수업 종료 관련 메시지 입니다.', '<' || v_title || '>\n위 수업이 종료되었습니다.\마이페이지 - 리뷰 작성을 통해 리뷰를 작성해주세요.', default, default);     
        end loop;
        
    END IF;
    END LOOP;
END;
/

-- 등급변경 proc 매일매일 확인
-- 등급변경시 메시지 전송 
create or replace procedure proc_comm_update
is
    v_loop number;
    v_cnt number;
    v_score number;
    v_tutor_id all_user.user_id%type;
begin

    select count(*)
    into v_loop
    from all_user
    where comm_grade not in('US','AD');
    
    for i in 1.. v_loop loop
        select user_id
        into v_tutor_id
        from (
                select user_id, rownum rnum
                from all_user
                where comm_grade not in('US','AD'))
        where rnum = i;
    
        select avg(review_score), count(*)
        into v_score, v_cnt
        from review
        where tutor_id = v_tutor_id;
    
        if v_cnt > 40 and v_score > 4 then
            update all_user set comm_grade = 'T1' where user_id = v_tutor_id;
            insert into message values(seq_msg_no.nextval, v_tutor_id, '등급변경 관련 메시지입니다.', 'T1 등급으로 변경되셨습니다.', default, default);        
        elsif v_cnt >30 and v_score > 3 then
            update all_user set comm_grade = 'T2' where user_id = v_tutor_id;
            insert into message values(seq_msg_no.nextval, v_tutor_id, '등급변경 관련 메시지입니다.', 'T2 등급으로 변경되셨습니다.', default, default);
        elsif v_cnt >20 and v_score > 2 then
            update all_user set comm_grade = 'T3' where user_id = v_tutor_id;
            insert into message values(seq_msg_no.nextval, v_tutor_id, '등급변경 관련 메시지입니다.', 'T3 등급으로 변경되셨습니다.', default, default);
        else
            update all_user set comm_grade = 'T4' where user_id = v_tutor_id;
            insert into message values(seq_msg_no.nextval, v_tutor_id, '등급변경 관련 메시지입니다.', 'T4 등급으로 변경되셨습니다.', default, default);
        end if;
        
    end loop;
end;
/
     
--================================================================
--                         CONTENTS :SCHEDULER
--
--
--================================================================

--프로그램 생성 : CLASS_END_CK_PROC : PROC_CLASS_END_CK 저장 프로시저 사용
BEGIN
    DBMS_SCHEDULER.CREATE_PROGRAM
    (
    PROGRAM_NAME =>'CLASS_END_CK_PROC',
    PROGRAM_ACTION => 'PROC_CLASS_END_CK',
    PROGRAM_TYPE =>'STORED_PROCEDURE',
    COMMENTS =>'CLASS_END CHECKER',
    ENABLED => TRUE
    );
END;
/

--스케줄러 생성 : SCHEDULE_DAILY_AM_00 : 매일 자정, 1번 실행되는 스케줄러
BEGIN 
    DBMS_SCHEDULER.CREATE_SCHEDULE(
    SCHEDULE_NAME => 'SCHEDULE_DAILY_AM_00',
    START_DATE => SYSDATE,
    END_DATE => NULL,
    REPEAT_INTERVAL  => 'FREQ=DAILY;INTERVAL=1;BYHOUR=00;BYMINUTE=0;BYSECOND=0;',
    COMMENTS => '매일 자정 실행'
    );
END;
/

--프로그램 + 스케줄러 연동 : 클래스 강의 날짜 : JOB_CK_END_DAILY
BEGIN
    DBMS_SCHEDULER.CREATE_JOB(
    JOB_NAME => 'JOB_CK_CLASS_END_DAILY',
    PROGRAM_NAME => 'CLASS_END_CK_PROC',
    SCHEDULE_NAME => 'SCHEDULE_DAILY_AM_00',
    COMMENTS => '수업종료 여부 확인 프로시저 등록',
    ENABLED => TRUE
    );
END;
/

--프로그램 생성 : ALLPY_END_CK_PROC : PROC_APPLY_END_CK 저장 프로시저 사용
BEGIN
    DBMS_SCHEDULER.CREATE_PROGRAM
    (
    PROGRAM_NAME =>'ALLPY_END_CK_PROC',
    PROGRAM_ACTION => 'PROC_APPLY_END_CK',
    PROGRAM_TYPE =>'STORED_PROCEDURE',
    COMMENTS =>'APPLY_END CHECKER',
    ENABLED => TRUE
    );
END;
/

--프로그램 + 스캐줄러 연동 : 클래스 접수 마감일 확인 : JOB_CK_APPLY_END_DAILY
BEGIN
    DBMS_SCHEDULER.CREATE_JOB(
    JOB_NAME => 'JOB_CK_APPLY_END_DAILY',
    PROGRAM_NAME => 'ALLPY_END_CK_PROC',
    SCHEDULE_NAME => 'SCHEDULE_DAILY_AM_00',
    COMMENTS => '클래스 접수 마감 확인 프로시저 등록',
    ENABLED => TRUE
    );
END;
/

--프로그램 생성 : proc-뭐시기뭐시기 : proc 저장용 프로시져
BEGIN
    DBMS_SCHEDULER.CREATE_PROGRAM
    (
    PROGRAM_NAME =>'COMM_UPDATE_PROC',
    PROGRAM_ACTION => 'PROC_COMM_UPDATE',
    PROGRAM_TYPE =>'STORED_PROCEDURE',
    COMMENTS =>'튜터 등급 조정에 사용되는 프로그램입니다.',
    ENABLED => TRUE
    );
END;
/
--프로그램 + 스케줄러 연동 : 튜터 등급
BEGIN
    DBMS_SCHEDULER.CREATE_JOB(
    JOB_NAME => 'JOB_CK_COMM_UPDATE_DAILY',
    PROGRAM_NAME =>'COMM_UPDATE_PROC',
    SCHEDULE_NAME => 'SCHEDULE_DAILY_AM_00',
    COMMENTS => '클래스 접수 마감 확인 프로시저 등록',
    ENABLED => TRUE
    );
END;
/

--EXEC DBMS_SCHEDULER.DROP_JOB('JOB_CK_CLASS_END_DAILY', false);
--
--EXEC DBMS_SCHEDULER.DROP_PROGRAM('CLASS_END_CK_PROC', false); 
--
--EXEC DBMS_SCHEDULER.DROP_SCHEDULE('SCHEDULE_DAILY_AM_00', false);
