CREATE TABLE hyperledger.blocks
(
    tx_date        Date,     -- only DATE from datetime
    tx_time        DateTime, -- only TIME from datetime

    blockchain_id  UInt32,
    block          UInt32,   -- block_number

    block_tx_count UInt32,   -- count transactions in the block
    block_hash     String,

    data_hash      String,
    previous_hash  String
) ENGINE = MergeTree ORDER BY block;

CREATE TABLE hyperledger.transactions
(
    tx_date               Date,     -- only DATE from datetime
    tx_time               DateTime, -- only TIME from datetime

    blockchain_id         UInt32,
    block                 UInt32,   -- block_number
    block_hash            String,

    tx_hash               String,   -- transaction_hash
    chaincode_name        String,   -- kiesnet-* (kiesnet-token, kiesnet-id, ...)

    tx_type               String,   -- function_name (transfer, pay, account, ...)
    validation_code       String,   -- VALID or INVALID

    payload_proposal_hash String,
    payload               String    -- need??
) ENGINE = MergeTree ORDER BY block;


CREATE TABLE hyperledger.write_set_txs
(
    tx_date        Date,     -- only DATE from datetime
    tx_time        DateTime, -- only TIME from datetime

    blockchain_id  UInt32,
    block          UInt32,   -- block_number
    block_hash     String,

    tx_hash        String,   -- transaction_hash
    chaincode_name String,   -- kiesnet-* (kiesnet-token, kiesnet-id, ...)

    set_index      UInt32,
    set_key        String,
    set_value      String,
    set_is_delete  UInt8
) ENGINE = MergeTree ORDER BY block;


CREATE TABLE IF NOT EXISTS hyperledger.transfers
(
    tx_date         Date,     -- only DATE from datetime
    tx_time         DateTime, -- only TIME from datetime

    blockchain_id   UInt32,
    block           UInt32,   -- block_number
    block_hash      String,

    tx_index        UInt32,
    tx_hash         String,
    tx_signer       String,
    tx_type         String,   -- function_name (transfer, pay, account, ...)
    validation_code String,   -- VALID or INVALID

    transfer_from   String,   -- address from
    transfer_to     String,   -- address to

    currency_id     UInt32,
    value           Decimal128(0)

) ENGINE = MergeTree ORDER BY block;

CREATE TABLE hyperledger.tx_transfers
(
    tx_date                          Date,          -- only DATE from datetime
    tx_time                          DateTime,      -- only TIME from datetime

    blockchain_id                    UInt32,
    block                            UInt32,        -- block_number
    block_hash                       String,

    tx_hash                          String,        -- transaction_hash
    chaincode_name                   String,        -- kiesnet-* (kiesnet-token, kiesnet-id, ...)

    tx_type                          String,        -- function_name (transfer, pay, account, ...)
    validation_code                  String,        -- VALID or INVALID

    -- PAYLOAD
    payload_balance_log_number       String,
    payload_balance_log_type         UInt32,
    payload_balance_log_rid          String,
    payload_balance_log_diff         Decimal128(0), -- type?? value transfer amount
    payload_balance_log_fee          Decimal128(0), -- type??
    payload_balance_log_amount       Decimal128(0), -- type?? balance amount before transfer
    payload_balance_log_memo         String,
    payload_balance_log_created_time DateTime

) ENGINE = MergeTree ORDER BY block;

CREATE TABLE hyperledger.tx_payments
(
    tx_date                          Date,          -- only DATE from datetime
    tx_time                          DateTime,      -- only TIME from datetime

    blockchain_id                    UInt32,
    block                            UInt32,        -- block_number
    block_hash                       String,

    tx_hash                          String,        -- transaction_hash
    chaincode_name                   String,        -- kiesnet-* (kiesnet-token, kiesnet-id, ...)

    tx_type                          String,        -- function_name (transfer, pay, account, ...)
    validation_code                  String,        -- VALID or INVALID

    -- PAYLOAD
    payload_pay_number               String,
    payload_pay_id                   String,
    payload_pay_amount               Decimal128(0),
    payload_pay_fee                  Decimal128(0),
    payload_pay_total_refund         Decimal128(0),
    payload_pay_rid                  String,
    payload_pay_order_id             UInt32,
    payload_pay_memo                 String,
    payload_pay_created_time         DateTime,

    payload_balance_log_number       String,
    payload_balance_log_type         UInt32,
    payload_balance_log_rid          String,
    payload_balance_log_diff         Decimal128(0), -- type?? value transfer amount
    payload_balance_log_fee          Decimal128(0), -- type??
    payload_balance_log_amount       Decimal128(0), -- type?? balance amount before transfer
    payload_balance_log_memo         String,
    payload_balance_log_created_time DateTime,
    payload_balance_log_pay_id       String
) ENGINE = MergeTree ORDER BY block;

CREATE TABLE hyperledger.tx_refunds
(
    tx_date              Date,          -- only DATE from datetime
    tx_time              DateTime,      -- only TIME from datetime

    blockchain_id        UInt32,
    block                UInt32,        -- block_number
    block_hash           String,

    tx_hash              String,        -- transaction_hash
    chaincode_name       String,        -- kiesnet-* (kiesnet-token, kiesnet-id, ...)

    tx_type              String,        -- function_name (transfer, pay, account, ...)
    validation_code      String,        -- VALID or INVALID

    -- PAYLOAD
    payload_balance_log  String,
    payload_type         UInt32,
    payload_rid          String,
    payload_diff         Decimal128(0), -- type?? value transfer amount
    payload_amount       Decimal128(0), -- type?? balance amount before transfer
    payload_memo         String,
    payload_created_time DateTime
) ENGINE = MergeTree ORDER BY block;

