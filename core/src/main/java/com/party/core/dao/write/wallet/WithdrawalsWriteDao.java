package com.party.core.dao.write.wallet;

import org.springframework.stereotype.Repository;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.wallet.Withdrawals;

@Repository
public interface WithdrawalsWriteDao extends BaseWriteDao<Withdrawals> {

}
