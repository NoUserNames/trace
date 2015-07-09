/**
 * 
 */
package rt.pojo;

/**
 * 短信功能-通讯录&群组关联关系数据模型
 * @author 张强
 *
 */
public class SMSContactExternalGroup {
	private SMSContact smsContact;
	private SMSContactGroup smsContactGroup;
	public SMSContact getSmsContact() {
		return smsContact;
	}
	public void setSmsContact(SMSContact smsContact) {
		this.smsContact = smsContact;
	}
	public SMSContactGroup getSmsContactGroup() {
		return smsContactGroup;
	}
	public void setSmsContactGroup(SMSContactGroup smsContactGroup) {
		this.smsContactGroup = smsContactGroup;
	}
}
