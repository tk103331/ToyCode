Set sapiX = CreateObject("SAPI.SpVoice")
sapiX.Volume = 100 '����
sapiX.Rate = 1 '�������� Խ��Խ��
FlagsAsync=1  'ͬ�����첽��1���첽
'���������ѡϵͳ�Ѱ�װ�����������Բ����У��ÿ�������˹���ѡ�õ�
'set colVoice=sapiX.getVoices() '��װ�ж��ٸ��������Ͽ�ѡ
'set sapiX.Voice=colVoice(1) 'ѡ��1������


strText=strText & "��Խ����Խ����Խ����Խ����Խ����Խ����Խ����Խ����Խ����Խ����Խ����Խ����Խ����Խ����Խ����Խ����ʼ��ֱ�����ϵ۴������г�����������ĥ�ϵ۰��¶��������·���� ��������͵��һ��Ȼ�����ϵۿ�ˡ��"

sapiX.Speak strtext,FlagsAsync  'ͬ�����첽
msgbox "���Դ��ڣ�ע��ͬ�����첽Ч��"
sapiX.Pause '��ͣSPEAK���첽��Ч�����MSGBOX����ȥ�����ڳ���һ�ξͿ���
MSGBOX "PAUSE����������"
sapiX.Resume